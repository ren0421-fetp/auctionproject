package org.fujitsu.training.codes.controller;

import org.fujitsu.training.codes.helper.RegistrationHelper;
import org.fujitsu.training.codes.model.form.RegistrationForm;
import org.fujitsu.training.codes.validator.RegistrationFormValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

	private final RegistrationHelper registrationHelper;
	private final RegistrationFormValidator registrationFormValidator;

	public RegistrationController(RegistrationHelper registrationHelper,
			RegistrationFormValidator registrationFormValidator) {
		this.registrationHelper = registrationHelper;
		this.registrationFormValidator = registrationFormValidator;
	}

	@InitBinder("registrationForm")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(registrationFormValidator);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String loadForm(Model model) {
		registrationHelper.prepareLoadForm(model);
		return "registerView";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submitForm(Model model, @Validated @ModelAttribute("registrationForm") RegistrationForm form,
			BindingResult result) {

		return registrationHelper.processSubmitForm(model, form, result);
	}
}
