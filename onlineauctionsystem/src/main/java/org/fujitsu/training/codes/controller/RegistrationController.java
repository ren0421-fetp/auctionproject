package org.fujitsu.training.codes.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.fujitsu.training.codes.dao.impl.RegistrationDaoImpl;
import org.fujitsu.training.codes.exceptions.DuplicateUsernameException;
//import org.fujitsu.training.codes.exceptions.DuplicateUsernameException;
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
    private final RegistrationFormValidator registrationFormValidator;
    private final RegistrationDaoImpl registrationService;

    public RegistrationController(RegistrationFormValidator registrationFormValidator,
            RegistrationDaoImpl registrationService) {
        this.registrationFormValidator = registrationFormValidator;
        this.registrationService = registrationService;
    }

    @InitBinder("registrationForm")
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(registrationFormValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String loadForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        populateReferenceData(model);
        return "registerView";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submitForm(
            Model model,
            @Validated @ModelAttribute("registrationForm") RegistrationForm form,
            BindingResult result) {

        if (result.hasErrors()) {
            populateReferenceData(model);
            return "registerView";
        }

        try {
            String username = registrationService.registerUser(form);
            model.addAttribute("registeredUsername", username);
            return "auth/registration_success";
        } catch (DuplicateUsernameException ex) {
            result.rejectValue("username", "duplicate", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            result.reject("registrationError", ex.getMessage());
        } catch (Exception ex) {
            result.reject("registrationError", "registration failed due to an unexpected error");
        }

        populateReferenceData(model);
        return "registerView";
    }

    private void populateReferenceData(Model model) {
        model.addAttribute("genderOpts", createGenderOptions());
        model.addAttribute("userTypeOpts", createUserTypeOptions());
        model.addAttribute("countryOpts", registrationService.getCountries());
        model.addAttribute("stateOpts", registrationService.getStates());
        model.addAttribute("cityOpts", registrationService.getCities());
    }

    private Map<String, String> createGenderOptions() {
        Map<String, String> opts = new LinkedHashMap<>();
        opts.put("male", "male");
        opts.put("female", "female");
        return opts;
    }

    private Map<String, String> createUserTypeOptions() {
        Map<String, String> opts = new LinkedHashMap<>();
        opts.put("seller", "seller");
        opts.put("bidder", "bidder");
        return opts;
    }
}
