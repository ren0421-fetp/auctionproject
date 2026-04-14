package org.fujitsu.training.codes.controller;

import org.fujitsu.training.codes.helper.AdminLocationHelper;
import org.fujitsu.training.codes.model.form.CityForm;
import org.fujitsu.training.codes.model.form.CountryForm;
import org.fujitsu.training.codes.model.form.StateForm;
import org.fujitsu.training.codes.validator.CityFormValidator;
import org.fujitsu.training.codes.validator.CountryFormValidator;
import org.fujitsu.training.codes.validator.StateFormValidator;
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
@RequestMapping("/admin/locations")
public class AdminLocationController {

	private static final String LOGIN_REDIRECT = "redirect:/app/login";

	private final AdminLocationHelper adminLocationHelper;
	private final CountryFormValidator countryFormValidator;
	private final StateFormValidator stateFormValidator;
	private final CityFormValidator cityFormValidator;

	public AdminLocationController(AdminLocationHelper adminLocationHelper, CountryFormValidator countryFormValidator,
			StateFormValidator stateFormValidator, CityFormValidator cityFormValidator) {
		this.adminLocationHelper = adminLocationHelper;
		this.countryFormValidator = countryFormValidator;
		this.stateFormValidator = stateFormValidator;
		this.cityFormValidator = cityFormValidator;
	}

	@InitBinder("countryForm")
	public void initCountryBinder(WebDataBinder binder) {
		binder.setValidator(countryFormValidator);
	}

	@InitBinder("stateForm")
	public void initStateBinder(WebDataBinder binder) {
		binder.setValidator(stateFormValidator);
	}

	@InitBinder("cityForm")
	public void initCityBinder(WebDataBinder binder) {
		binder.setValidator(cityFormValidator);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String loadPage(@RequestParam(value = "countryId", required = false) Integer countryId,
			@RequestParam(value = "stateId", required = false) Integer stateId,
			@RequestParam(value = "cityId", required = false) Integer cityId,
			@RequestParam(value = "success", required = false) String success, Model model, HttpSession session) {

		if (!isAdmin(session)) {
			return LOGIN_REDIRECT;
		}

		adminLocationHelper.prepareLoadPage(countryId, stateId, cityId, success, model);
		return "adminLocationView";
	}

	@RequestMapping(value = "/country/save", method = RequestMethod.POST)
	public String saveCountry(@Validated @ModelAttribute("countryForm") CountryForm form, BindingResult result,
			Model model, HttpSession session) {

		if (!isAdmin(session)) {
			return LOGIN_REDIRECT;
		}

		return adminLocationHelper.processSaveCountry(form, result, model);
	}

	@RequestMapping(value = "/state/save", method = RequestMethod.POST)
	public String saveState(@Validated @ModelAttribute("stateForm") StateForm form, BindingResult result, Model model,
			HttpSession session) {

		if (!isAdmin(session)) {
			return LOGIN_REDIRECT;
		}

		return adminLocationHelper.processSaveState(form, result, model);
	}

	@RequestMapping(value = "/city/save", method = RequestMethod.POST)
	public String saveCity(@Validated @ModelAttribute("cityForm") CityForm form, BindingResult result, Model model,
			HttpSession session) {

		if (!isAdmin(session)) {
			return LOGIN_REDIRECT;
		}

		return adminLocationHelper.processSaveCity(form, result, model);
	}

	@RequestMapping(value = "/country/delete", method = RequestMethod.POST)
	public String deleteCountry(@RequestParam("countryId") Integer countryId, Model model, HttpSession session) {

		if (!isAdmin(session)) {
			return LOGIN_REDIRECT;
		}

		return adminLocationHelper.processDeleteCountry(countryId, model);
	}

	@RequestMapping(value = "/state/delete", method = RequestMethod.POST)
	public String deleteState(@RequestParam("stateId") Integer stateId, Model model, HttpSession session) {

		if (!isAdmin(session)) {
			return LOGIN_REDIRECT;
		}

		return adminLocationHelper.processDeleteState(stateId, model);
	}

	@RequestMapping(value = "/city/delete", method = RequestMethod.POST)
	public String deleteCity(@RequestParam("cityId") Integer cityId, Model model, HttpSession session) {

		if (!isAdmin(session)) {
			return LOGIN_REDIRECT;
		}

		return adminLocationHelper.processDeleteCity(cityId, model);
	}

	private boolean isAdmin(HttpSession session) {
		String username = (String) session.getAttribute("loggedInUsername");
		String userType = (String) session.getAttribute("loggedInUserType");
		return username != null && userType != null && "admin".equalsIgnoreCase(userType);
	}
}
