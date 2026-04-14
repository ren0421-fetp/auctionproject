package org.fujitsu.training.codes.controller;

import org.fujitsu.training.codes.helper.SellerProfileHelper;
import org.fujitsu.training.codes.model.form.ChangePasswordForm;
import org.fujitsu.training.codes.model.form.SellerProfileForm;
import org.fujitsu.training.codes.validator.SellerProfileFormValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/seller/profile")
public class SellerProfileController {

	private static final String LOGIN_REDIRECT = "redirect:/app/login";

	private final SellerProfileHelper sellerProfileHelper;
	private final SellerProfileFormValidator sellerProfileFormValidator;

	public SellerProfileController(SellerProfileHelper sellerProfileHelper,
			SellerProfileFormValidator sellerProfileFormValidator) {
		this.sellerProfileHelper = sellerProfileHelper;
		this.sellerProfileFormValidator = sellerProfileFormValidator;
	}

	@InitBinder("sellerProfileForm")
	public void initProfileBinder(WebDataBinder binder) {
		binder.setValidator(sellerProfileFormValidator);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String loadProfile(Model model, HttpSession session) {
		String username = getSellerUsername(session);
		if (username == null) {
			return LOGIN_REDIRECT;
		}

		return sellerProfileHelper.prepareLoadProfile(username, model);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateProfile(@Validated @ModelAttribute("sellerProfileForm") SellerProfileForm form,
			BindingResult result, Model model, HttpSession session) {

		String username = getSellerUsername(session);
		if (username == null) {
			return LOGIN_REDIRECT;
		}

		return sellerProfileHelper.processUpdateProfile(form, result, model, username);
	}

	@RequestMapping(value = "/change-password", method = RequestMethod.POST)
	public String changePassword(@ModelAttribute("changePasswordForm") ChangePasswordForm form, Model model,
			HttpSession session) {

		String username = getSellerUsername(session);
		if (username == null) {
			return LOGIN_REDIRECT;
		}

		return sellerProfileHelper.processChangePassword(form, model, username);
	}

	private String getSellerUsername(HttpSession session) {
		String username = (String) session.getAttribute("loggedInUsername");
		String userType = (String) session.getAttribute("loggedInUserType");

		if (username == null || userType == null || !"seller".equalsIgnoreCase(userType)) {
			return null;
		}

		return username;
	}
}
