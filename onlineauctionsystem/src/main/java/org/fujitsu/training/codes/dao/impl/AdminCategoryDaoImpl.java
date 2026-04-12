package org.fujitsu.training.codes.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.model.data.Category;
import org.fujitsu.training.codes.model.form.CategoryForm;
import org.springframework.stereotype.Repository;

@Repository
public class AdminCategoryDaoImpl {
    private static final Logger logger = LogManager.getLogger(AdminCategoryDaoImpl.class);
    private final SqlSessionFactory ssf;

    public AdminCategoryDaoImpl(SqlSessionFactory ssf) {
        this.ssf = ssf;
    }

    public List<Category> getAllCategories() {
        try (SqlSession sess = ssf.openSession()) {
            return sess.selectList("org.fujitsu.training.codes.dao.CategoryDao.selectAllCategories");
        }
    }

    public CategoryForm getCategoryForm(Integer catId) {
        try (SqlSession sess = ssf.openSession()) {
            Category category = sess.selectOne("org.fujitsu.training.codes.dao.CategoryDao.selectCategoryById", catId);
            if (category == null) {
                return null;
            }

            CategoryForm form = new CategoryForm();
            form.setCatId(category.getCatId());
            form.setCatName(category.getCatName());
            return form;
        }
    }

    public void saveCategory(CategoryForm form) throws Exception {
        logger.info("Saving category: {}", form.getCatName());

        SqlSession sess = ssf.openSession();
        try {
            java.util.Map<String, Object> duplicateParams = new java.util.HashMap<>();
            duplicateParams.put("catName", form.getCatName());
            duplicateParams.put("catId", form.getCatId());

            Integer duplicateCount = sess.selectOne(
                    "org.fujitsu.training.codes.dao.CategoryDao.countCategoryByName",
                    duplicateParams);

            if (duplicateCount != null && duplicateCount > 0) {
                throw new IllegalArgumentException("Category name already exists.");
            }

            Category category = new Category();
            category.setCatId(form.getCatId());
            category.setCatName(form.getCatName().trim());

            if (form.getCatId() == null) {
                Integer inserted = sess.insert("org.fujitsu.training.codes.dao.CategoryDao.insertCategory", category);
                if (inserted == null || inserted != 1) {
                    throw new IllegalStateException("Failed to add category.");
                }
            } else {
                Integer updated = sess.update("org.fujitsu.training.codes.dao.CategoryDao.updateCategory", category);
                if (updated == null || updated != 1) {
                    throw new IllegalStateException("Failed to update category.");
                }
            }

            sess.commit();
        } catch (Exception ex) {
            sess.rollback();
            logger.error("Failed to save category {}: {}", form.getCatName(), ex.getMessage(), ex);
            throw ex;
        } finally {
            sess.close();
        }
    }

    public void deleteCategory(Integer catId) throws Exception {
        logger.info("Deleting category {}", catId);

        SqlSession sess = ssf.openSession();
        try {
            Integer productUsageCount = sess.selectOne(
                    "org.fujitsu.training.codes.dao.CategoryDao.countProductsUsingCategory",
                    catId);

            if (productUsageCount != null && productUsageCount > 0) {
                throw new IllegalArgumentException("Category cannot be deleted because products are using it.");
            }

            Integer deleted = sess.delete("org.fujitsu.training.codes.dao.CategoryDao.deleteCategoryById", catId);
            if (deleted == null || deleted != 1) {
                throw new IllegalStateException("Failed to delete category.");
            }

            sess.commit();
        } catch (Exception ex) {
            sess.rollback();
            logger.error("Failed to delete category {}: {}", catId, ex.getMessage(), ex);
            throw ex;
        } finally {
            sess.close();
        }
    }
}
