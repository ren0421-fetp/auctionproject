package org.fujitsu.training.codes.dao.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.model.data.Category;
import org.fujitsu.training.codes.model.data.Product;
import org.fujitsu.training.codes.model.data.User;
import org.fujitsu.training.codes.model.form.ProductForm;
import org.springframework.stereotype.Repository;

@Repository
public class AdminProductDaoImpl {
    private static final Logger logger = LogManager.getLogger("admin-flow");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    private final SqlSessionFactory ssf;

    public AdminProductDaoImpl(SqlSessionFactory ssf) {
        this.ssf = ssf;
    }

    public List<Product> getAllProducts(String status, String sellerUsername) {
    	logger.info("Getting all products");
        try (SqlSession sess = ssf.openSession()) {
            Map<String, Object> params = new HashMap<>();
            params.put("status", status);
            params.put("sellerUsername", sellerUsername);
            logger.info("Returning all products");
            return sess.selectList("org.fujitsu.training.codes.dao.ProductDao.selectAllProductsForAdmin", params);
        } catch (Exception ex) {
            logger.error("Failed to load admin products: {}", ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    public List<Category> getCategories() {
    	logger.info("Getting categories");
        try (SqlSession sess = ssf.openSession()) {
        	logger.info("Returning categories");
        	return sess.selectList("org.fujitsu.training.codes.dao.CategoryDao.selectAllCategories");
        } catch (Exception ex) {
            logger.error("Failed to load categories: {}", ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    public List<User> getSellers() {
        try (SqlSession sess = ssf.openSession()) {
            return sess.selectList("org.fujitsu.training.codes.dao.UserDao.selectUsersByType", "seller");
        } catch (Exception ex) {
            logger.error("Failed to load sellers: {}", ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    public ProductForm getProductForm(Integer productId) {
    	logger.info("Getting product form");
        try (SqlSession sess = ssf.openSession()) {
            Product product = sess.selectOne("org.fujitsu.training.codes.dao.ProductDao.selectProductByIdForAdmin", productId);
            if (product == null) {
                return null;
            }

            ProductForm form = new ProductForm();
            form.setProductId(product.getProductId());
            form.setCatId(product.getCatId());
            form.setSellerUsername(product.getSellerUsername());
            form.setProductName(product.getProductName());
            form.setDescription(product.getDescription());
            form.setMinBidPrice(product.getMinBidPrice());
            form.setStatus(product.getStatus());
            form.setPhotoPath(product.getPhotoPath());
            form.setCurrentPhotoPath(product.getPhotoPath());
            form.setStartDate(product.getStartDate().format(FORMATTER));
            form.setEndDate(product.getEndDate().format(FORMATTER));
            logger.info("Returning product form");
            return form;
        } catch (Exception ex) {
            logger.error("Failed to load product form for productId={}: {}", productId, ex.getMessage(), ex);
            return null;
        }
    }

    public Integer saveProduct(ProductForm form) throws Exception {
        logger.info("Starting admin product save. productId={}, productName={}",
                form.getProductId(), form.getProductName());

        SqlSession sess = ssf.openSession();
        try {
            Integer categoryCount = sess.selectOne("org.fujitsu.training.codes.dao.CategoryDao.countCategoryById", form.getCatId());
            if (categoryCount == null || categoryCount == 0) {
                throw new IllegalArgumentException("Selected category does not exist.");
            }

            User seller = sess.selectOne("org.fujitsu.training.codes.dao.UserDao.selectByUsername", form.getSellerUsername());
            if (seller == null || !"seller".equalsIgnoreCase(seller.getUserType())) {
                throw new IllegalArgumentException("Selected seller is invalid.");
            }

            Product product = mapFormToProduct(form);

            if (form.getProductId() == null) {
                Integer inserted = sess.insert("org.fujitsu.training.codes.dao.ProductDao.insertProduct", product);
                if (inserted == null || inserted != 1) {
                    throw new IllegalStateException("Failed to add product.");
                }
            } else {
                Integer updated = sess.update("org.fujitsu.training.codes.dao.ProductDao.updateProductForAdmin", product);
                if (updated == null || updated != 1) {
                    throw new IllegalStateException("Failed to update product.");
                }
            }

            sess.commit();
            logger.info("Admin product save completed. productId={}, productName={}",
                    product.getProductId(), form.getProductName());
            return product.getProductId();
        } catch (Exception ex) {
            sess.rollback();
            logger.error("Admin product save failed. productId={}, productName={}: {}",
                    form.getProductId(), form.getProductName(), ex.getMessage(), ex);
            throw ex;
        } finally {
            sess.close();
        }
    }

    public void deleteProduct(Integer productId) throws Exception {
        logger.info("Starting admin product delete. productId={}", productId);

        SqlSession sess = ssf.openSession();
        try {
            Integer bidCount = sess.selectOne("org.fujitsu.training.codes.dao.BidDao.countBidsByProductId", productId);
            if (bidCount != null && bidCount > 0) {
                throw new IllegalArgumentException("Product cannot be deleted because bids already exist. Close it instead.");
            }

            Integer deleted = sess.delete("org.fujitsu.training.codes.dao.ProductDao.deleteProductById", productId);
            if (deleted == null || deleted != 1) {
                throw new IllegalStateException("Failed to delete product.");
            }

            sess.commit();
            logger.info("Admin product delete completed. productId={}", productId);
        } catch (Exception ex) {
            sess.rollback();
            logger.error("Admin product delete failed. productId={}: {}", productId, ex.getMessage(), ex);
            throw ex;
        } finally {
            sess.close();
        }
    }

    public void closeProduct(Integer productId) throws Exception {
        logger.info("Starting admin product close. productId={}", productId);

        SqlSession sess = ssf.openSession();
        try {
            Integer updated = sess.update("org.fujitsu.training.codes.dao.ProductDao.closeProductById", productId);
            if (updated == null || updated != 1) {
                throw new IllegalStateException("Failed to close product.");
            }

            sess.commit();
            logger.info("Admin product close completed. productId={}", productId);
        } catch (Exception ex) {
            sess.rollback();
            logger.error("Admin product close failed. productId={}: {}", productId, ex.getMessage(), ex);
            throw ex;
        } finally {
            sess.close();
        }
    }

    private Product mapFormToProduct(ProductForm form) {
        Product product = new Product();
        product.setProductId(form.getProductId());
        product.setCatId(form.getCatId());
        product.setSellerUsername(form.getSellerUsername());
        product.setProductName(form.getProductName());
        product.setDescription(form.getDescription());
        product.setMinBidPrice(form.getMinBidPrice());
        product.setStatus(form.getStatus());
        product.setPhotoPath(form.getPhotoPath());
        product.setStartDate(LocalDateTime.parse(form.getStartDate(), FORMATTER));
        product.setEndDate(LocalDateTime.parse(form.getEndDate(), FORMATTER));
        return product;
    }
}
