package org.fujitsu.training.codes.helper;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.RegistrationDaoImpl;
import org.fujitsu.training.codes.exceptions.DuplicateUsernameException;
import org.fujitsu.training.codes.model.form.RegistrationForm;
import org.fujitsu.training.codes.service.FileService;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Component
public class RegistrationHelper {
	private static final Logger logger = LogManager.getLogger("auth-flow");

	private final RegistrationDaoImpl registrationDaoImpl;
	private final FileService fileService;

	public RegistrationHelper(RegistrationDaoImpl registrationDaoImpl, FileService fileService) {
		this.registrationDaoImpl = registrationDaoImpl;
		this.fileService = fileService;
	}

	public void prepareLoadForm(Model model) {
		logger.info("Loading registration page.");
		model.addAttribute("registrationForm", new RegistrationForm());
		populateReferenceData(model);
		logger.info("Registration page loaded.");
	}

	public String processSubmitForm(Model model, RegistrationForm form, BindingResult result) {
		logger.info("Processing registration for username={}.", form.getUsername());

		if (result.hasErrors()) {
			populateReferenceData(model);
			logger.warn("Registration validation failed for username={}.", form.getUsername());
			return "registerView";
		}

		try {
			handleProfilePhoto(form);
			String username = registrationDaoImpl.registerUser(form);
			model.addAttribute("registeredUsername", username);
			logger.info("Registration completed successfully for username={}.", username);
			return "redirect:/app/login";
		} catch (DuplicateUsernameException ex) {
			logger.error("Registration failed for username={}: {}", form.getUsername(), ex.getMessage(), ex);
			result.rejectValue("username", "duplicate", ex.getMessage());
		} catch (IllegalArgumentException ex) {
			logger.error("Registration failed for username={}: {}", form.getUsername(), ex.getMessage(), ex);
			result.reject("registrationError", ex.getMessage());
		} catch (Exception ex) {
			logger.error("Registration failed for username={}: {}", form.getUsername(), ex.getMessage(), ex);
			result.reject("registrationError", "Registration failed due to an unexpected system error.");
		}

		populateReferenceData(model);
		return "registerView";
	}

	private void handleProfilePhoto(RegistrationForm form) throws Exception {
		if (form.getPhotoFile() == null || form.getPhotoFile().isEmpty()) {
			return;
		}

		String photoPath = fileService.saveFile(form.getPhotoFile(), "profile", form.getUsername());
		form.setPhotoPath(photoPath);
	}

	private void populateReferenceData(Model model) {
		model.addAttribute("genderOpts", createGenderOptions());
		model.addAttribute("userTypeOpts", createUserTypeOptions());
		model.addAttribute("countryOpts", registrationDaoImpl.getCountries());
		model.addAttribute("stateOpts", registrationDaoImpl.getStates());
		model.addAttribute("cityOpts", registrationDaoImpl.getCities());
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
