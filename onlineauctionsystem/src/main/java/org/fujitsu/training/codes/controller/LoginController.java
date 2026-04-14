package org.fujitsu.training.codes.controller;

import org.fujitsu.training.codes.helper.LoginHelper;
import org.fujitsu.training.codes.model.form.LoginForm;
import org.fujitsu.training.codes.validator.LoginFormValidator;
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
@RequestMapping("/login")
public class LoginController {

	private final LoginHelper loginHelper;
	private final LoginFormValidator loginFormValidator;

	public LoginController(LoginHelper loginHelper, LoginFormValidator loginFormValidator) {
		this.loginHelper = loginHelper;
		this.loginFormValidator = loginFormValidator;
	}

	@InitBinder("loginForm")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(loginFormValidator);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String loadForm(Model model) {
		loginHelper.prepareLoadForm(model);
		return "loginView";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submitForm(@Validated @ModelAttribute("loginForm") LoginForm form, BindingResult result, Model model,
			HttpSession session) {

		return loginHelper.processSubmitForm(form, result, session);
	}
}
