/*package org.fujitsu.training.codes.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.AdminCategoryDaoImpl;
import org.fujitsu.training.codes.model.form.CategoryForm;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Component
public class AdminCategoryHelper {
    private static final Logger logger = LogManager.getLogger(AdminCategoryHelper.class);

    private static final String VIEW_NAME = "adminCategoryView";
    private static final String SUCCESS_REDIRECT = "redirect:/app/admin/categories?success=1";
    private static final String CATEGORY_REDIRECT = "redirect:/app/admin/categories";

    private final AdminCategoryDaoImpl adminCategoryDaoImpl;

    public AdminCategoryHelper(AdminCategoryDaoImpl adminCategoryDaoImpl) {
        this.adminCategoryDaoImpl = adminCategoryDaoImpl;
    }

    public void prepareLoadCategories(String success, Model model) {
        model.addAttribute("categoryForm", new CategoryForm());
        model.addAttribute("categories", adminCategoryDaoImpl.getAllCategories());

        if ("1".equals(success)) {
            model.addAttribute("saveSuccess", "Category saved successfully.");
        }
    }

    public String prepareEditForm(Integer catId, Model model) {
        CategoryForm form = adminCategoryDaoImpl.getCategoryForm(catId);
        if (form == null) {
            return CATEGORY_REDIRECT;
        }

        populatePage(model, form);
        return VIEW_NAME;
    }

    public String processSaveCategory(CategoryForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            populatePage(model, form);
            return VIEW_NAME;
        }

        try {
            adminCategoryDaoImpl.saveCategory(form);
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Failed to save category: {}", ex.getMessage(), ex);
            populatePage(model, form);
            model.addAttribute("saveError", ex.getMessage());
            return VIEW_NAME;
        }
    }

    public String processDeleteCategory(Integer catId, Model model) {
        try {
            adminCategoryDaoImpl.deleteCategory(catId);
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Failed to delete category {}: {}", catId, ex.getMessage(), ex);
            model.addAttribute("categoryForm", new CategoryForm());
            model.addAttribute("categories", adminCategoryDaoImpl.getAllCategories());
            model.addAttribute("saveError", ex.getMessage());
            return VIEW_NAME;
        }
    }

    private void populatePage(Model model, CategoryForm form) {
        model.addAttribute("categoryForm", form);
        model.addAttribute("categories", adminCategoryDaoImpl.getAllCategories());
        model.addAttribute("editMode", form.getCatId() != null);
    }
}
*/

package org.fujitsu.training.codes.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.AdminCategoryDaoImpl;
import org.fujitsu.training.codes.model.form.CategoryForm;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Component
public class AdminCategoryHelper {
    private static final Logger logger = LogManager.getLogger("admin-flow");

    private static final String VIEW_NAME = "adminCategoryView";
    private static final String SUCCESS_REDIRECT = "redirect:/app/admin/categories?success=1";
    private static final String CATEGORY_REDIRECT = "redirect:/app/admin/categories";

    private final AdminCategoryDaoImpl adminCategoryDaoImpl;

    public AdminCategoryHelper(AdminCategoryDaoImpl adminCategoryDaoImpl) {
        this.adminCategoryDaoImpl = adminCategoryDaoImpl;
    }

    public void prepareLoadCategories(String success, Model model) {
        logger.info("Loading admin category page.");
        model.addAttribute("categoryForm", new CategoryForm());
        model.addAttribute("categories", adminCategoryDaoImpl.getAllCategories());

        if ("1".equals(success)) {
            model.addAttribute("saveSuccess", "Category saved successfully.");
        }

        logger.info("Admin category page loaded.");
    }

    public String prepareEditForm(Integer catId, Model model) {
        logger.info("Loading admin category edit form. catId={}", catId);
        CategoryForm form = adminCategoryDaoImpl.getCategoryForm(catId);
        if (form == null) {
            logger.warn("Admin category edit form load failed. catId={} not found.", catId);
            return CATEGORY_REDIRECT;
        }

        populatePage(model, form);
        logger.info("Admin category edit form loaded. catId={}", catId);
        return VIEW_NAME;
    }

    public String processSaveCategory(CategoryForm form, BindingResult result, Model model) {
        logger.info("Processing category save. catId={}, categoryName={}",
                form.getCatId(), form.getCatName());

        if (result.hasErrors()) {
            populatePage(model, form);
            logger.warn("Category validation failed. catId={}, categoryName={}",
                    form.getCatId(), form.getCatName());
            return VIEW_NAME;
        }

        try {
            adminCategoryDaoImpl.saveCategory(form);
            logger.info("Category save completed. catId={}, categoryName={}",
                    form.getCatId(), form.getCatName());
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Failed to save category: {}", ex.getMessage(), ex);
            populatePage(model, form);
            model.addAttribute("saveError", ex.getMessage());
            return VIEW_NAME;
        }
    }

    public String processDeleteCategory(Integer catId, Model model) {
        logger.info("Processing category delete. catId={}", catId);
        try {
            adminCategoryDaoImpl.deleteCategory(catId);
            logger.info("Category delete completed. catId={}", catId);
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Failed to delete category {}: {}", catId, ex.getMessage(), ex);
            model.addAttribute("categoryForm", new CategoryForm());
            model.addAttribute("categories", adminCategoryDaoImpl.getAllCategories());
            model.addAttribute("saveError", ex.getMessage());
            return VIEW_NAME;
        }
    }

    private void populatePage(Model model, CategoryForm form) {
        model.addAttribute("categoryForm", form);
        model.addAttribute("categories", adminCategoryDaoImpl.getAllCategories());
        model.addAttribute("editMode", form.getCatId() != null);
    }
}
