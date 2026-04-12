package org.fujitsu.training.codes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.AdminCategoryDaoImpl;
import org.fujitsu.training.codes.model.form.CategoryForm;
import org.fujitsu.training.codes.validator.CategoryFormValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {
    private static final Logger logger = LogManager.getLogger(AdminCategoryController.class);

    private final AdminCategoryDaoImpl adminCategoryDaoImpl;
    private final CategoryFormValidator categoryFormValidator;

    public AdminCategoryController(AdminCategoryDaoImpl adminCategoryDaoImpl,
            CategoryFormValidator categoryFormValidator) {
        this.adminCategoryDaoImpl = adminCategoryDaoImpl;
        this.categoryFormValidator = categoryFormValidator;
    }

    @InitBinder("categoryForm")
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(categoryFormValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String loadCategories(
            @RequestParam(value = "success", required = false) String success,
            Model model,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/app/login";
        }

        model.addAttribute("categoryForm", new CategoryForm());
        model.addAttribute("categories", adminCategoryDaoImpl.getAllCategories());

        if ("1".equals(success)) {
            model.addAttribute("saveSuccess", "Category saved successfully.");
        }

        return "adminCategoryView";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String loadEditForm(@RequestParam("catId") Integer catId,
            Model model,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/app/login";
        }

        CategoryForm form = adminCategoryDaoImpl.getCategoryForm(catId);
        if (form == null) {
            return "redirect:/app/admin/categories";
        }

        model.addAttribute("categoryForm", form);
        model.addAttribute("categories", adminCategoryDaoImpl.getAllCategories());
        model.addAttribute("editMode", true);
        return "adminCategoryView";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveCategory(
            @Validated @ModelAttribute("categoryForm") CategoryForm form,
            BindingResult result,
            Model model,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/app/login";
        }

        if (result.hasErrors()) {
            model.addAttribute("categories", adminCategoryDaoImpl.getAllCategories());
            model.addAttribute("editMode", form.getCatId() != null);
            return "adminCategoryView";
        }

        try {
            adminCategoryDaoImpl.saveCategory(form);
            return "redirect:/app/admin/categories?success=1";
        } catch (Exception ex) {
            logger.error("Failed to save category: {}", ex.getMessage(), ex);
            model.addAttribute("categories", adminCategoryDaoImpl.getAllCategories());
            model.addAttribute("editMode", form.getCatId() != null);
            model.addAttribute("saveError", ex.getMessage());
            return "adminCategoryView";
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteCategory(@RequestParam("catId") Integer catId,
            Model model,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/app/login";
        }

        try {
            adminCategoryDaoImpl.deleteCategory(catId);
            return "redirect:/app/admin/categories?success=1";
        } catch (Exception ex) {
            logger.error("Failed to delete category {}: {}", catId, ex.getMessage(), ex);
            model.addAttribute("categoryForm", new CategoryForm());
            model.addAttribute("categories", adminCategoryDaoImpl.getAllCategories());
            model.addAttribute("saveError", ex.getMessage());
            return "adminCategoryView";
        }
    }

    private boolean isAdmin(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUsername");
        String userType = (String) session.getAttribute("loggedInUserType");
        return username != null && userType != null && "admin".equalsIgnoreCase(userType);
    }
}
