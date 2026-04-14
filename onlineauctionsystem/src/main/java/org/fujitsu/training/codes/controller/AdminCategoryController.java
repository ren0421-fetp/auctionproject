package org.fujitsu.training.codes.controller;

import org.fujitsu.training.codes.helper.AdminCategoryHelper;
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

	private static final String LOGIN_REDIRECT = "redirect:/app/login";
	private static final String VIEW_NAME = "adminCategoryView";

	private final AdminCategoryHelper adminCategoryHelper;
	private final CategoryFormValidator categoryFormValidator;

	public AdminCategoryController(AdminCategoryHelper adminCategoryHelper,
			CategoryFormValidator categoryFormValidator) {
		this.adminCategoryHelper = adminCategoryHelper;
		this.categoryFormValidator = categoryFormValidator;
	}

	@InitBinder("categoryForm")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(categoryFormValidator);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String loadCategories(@RequestParam(value = "success", required = false) String success, Model model,
			HttpSession session) {

		if (!isAdmin(session)) {
			return LOGIN_REDIRECT;
		}

		adminCategoryHelper.prepareLoadCategories(success, model);
		return VIEW_NAME;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String loadEditForm(@RequestParam("catId") Integer catId, Model model, HttpSession session) {

		if (!isAdmin(session)) {
			return LOGIN_REDIRECT;
		}

		return adminCategoryHelper.prepareEditForm(catId, model);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveCategory(@Validated @ModelAttribute("categoryForm") CategoryForm form, BindingResult result,
			Model model, HttpSession session) {

		if (!isAdmin(session)) {
			return LOGIN_REDIRECT;
		}

		return adminCategoryHelper.processSaveCategory(form, result, model);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteCategory(@RequestParam("catId") Integer catId, Model model, HttpSession session) {

		if (!isAdmin(session)) {
			return LOGIN_REDIRECT;
		}

		return adminCategoryHelper.processDeleteCategory(catId, model);
	}

	private boolean isAdmin(HttpSession session) {
		String username = (String) session.getAttribute("loggedInUsername");
		String userType = (String) session.getAttribute("loggedInUserType");
		return username != null && userType != null && "admin".equalsIgnoreCase(userType);
	}
}
