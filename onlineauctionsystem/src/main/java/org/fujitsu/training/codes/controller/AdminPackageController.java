package org.fujitsu.training.codes.controller;

import org.fujitsu.training.codes.helper.AdminPackageHelper;
import org.fujitsu.training.codes.model.form.PackageForm;
import org.fujitsu.training.codes.validator.PackageFormValidator;
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
@RequestMapping("/admin/packages")
public class AdminPackageController {

	private static final String LOGIN_REDIRECT = "redirect:/app/login";
	private static final String VIEW_NAME = "adminPackageView";

	private final AdminPackageHelper adminPackageHelper;
	private final PackageFormValidator packageFormValidator;

	public AdminPackageController(AdminPackageHelper adminPackageHelper, PackageFormValidator packageFormValidator) {
		this.adminPackageHelper = adminPackageHelper;
		this.packageFormValidator = packageFormValidator;
	}

	@InitBinder("packageForm")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(packageFormValidator);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String loadPackages(@RequestParam(value = "success", required = false) String success, Model model,
			HttpSession session) {

		if (!isAdmin(session)) {
			return LOGIN_REDIRECT;
		}

		adminPackageHelper.prepareLoadPackages(success, model);
		return VIEW_NAME;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String loadEditForm(@RequestParam("packageId") Integer packageId, Model model, HttpSession session) {

		if (!isAdmin(session)) {
			return LOGIN_REDIRECT;
		}

		return adminPackageHelper.prepareEditForm(packageId, model);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String savePackage(@Validated @ModelAttribute("packageForm") PackageForm form, BindingResult result,
			Model model, HttpSession session) {

		if (!isAdmin(session)) {
			return LOGIN_REDIRECT;
		}

		return adminPackageHelper.processSavePackage(form, result, model);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deletePackage(@RequestParam("packageId") Integer packageId, Model model, HttpSession session) {

		if (!isAdmin(session)) {
			return LOGIN_REDIRECT;
		}

		return adminPackageHelper.processDeletePackage(packageId, model);
	}

	private boolean isAdmin(HttpSession session) {
		String username = (String) session.getAttribute("loggedInUsername");
		String userType = (String) session.getAttribute("loggedInUserType");
		return username != null && userType != null && "admin".equalsIgnoreCase(userType);
	}
}
