package org.fujitsu.training.codes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.AdminLocationDaoImpl;
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
    private static final Logger logger = LogManager.getLogger(AdminLocationController.class);

    private final AdminLocationDaoImpl adminLocationDaoImpl;
    private final CountryFormValidator countryFormValidator;
    private final StateFormValidator stateFormValidator;
    private final CityFormValidator cityFormValidator;

    public AdminLocationController(AdminLocationDaoImpl adminLocationDaoImpl,
            CountryFormValidator countryFormValidator,
            StateFormValidator stateFormValidator,
            CityFormValidator cityFormValidator) {
        this.adminLocationDaoImpl = adminLocationDaoImpl;
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
    public String loadPage(
            @RequestParam(value = "countryId", required = false) Integer countryId,
            @RequestParam(value = "stateId", required = false) Integer stateId,
            @RequestParam(value = "cityId", required = false) Integer cityId,
            @RequestParam(value = "success", required = false) String success,
            Model model,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/app/login";
        }

        CountryForm countryForm = countryId != null ? adminLocationDaoImpl.getCountryForm(countryId) : new CountryForm();
        StateForm stateForm = stateId != null ? adminLocationDaoImpl.getStateForm(stateId) : new StateForm();
        CityForm cityForm = cityId != null ? adminLocationDaoImpl.getCityForm(cityId) : new CityForm();

        populatePage(model, countryForm == null ? new CountryForm() : countryForm,
                stateForm == null ? new StateForm() : stateForm,
                cityForm == null ? new CityForm() : cityForm);

        if ("1".equals(success)) {
            model.addAttribute("locationSuccess", "Location data saved successfully.");
        }

        return "adminLocationView";
    }

    @RequestMapping(value = "/country/save", method = RequestMethod.POST)
    public String saveCountry(@Validated @ModelAttribute("countryForm") CountryForm form,
            BindingResult result, Model model, HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/app/login";
        }

        if (result.hasErrors()) {
            populatePage(model, form, new StateForm(), new CityForm());
            return "adminLocationView";
        }

        try {
            adminLocationDaoImpl.saveCountry(form);
            return "redirect:/app/admin/locations?success=1";
        } catch (Exception ex) {
            logger.error("Failed to save country: {}", ex.getMessage(), ex);
            populatePage(model, form, new StateForm(), new CityForm());
            model.addAttribute("locationError", ex.getMessage());
            return "adminLocationView";
        }
    }

    @RequestMapping(value = "/state/save", method = RequestMethod.POST)
    public String saveState(@Validated @ModelAttribute("stateForm") StateForm form,
            BindingResult result, Model model, HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/app/login";
        }

        if (result.hasErrors()) {
            populatePage(model, new CountryForm(), form, new CityForm());
            return "adminLocationView";
        }

        try {
            adminLocationDaoImpl.saveState(form);
            return "redirect:/app/admin/locations?success=1";
        } catch (Exception ex) {
            logger.error("Failed to save state: {}", ex.getMessage(), ex);
            populatePage(model, new CountryForm(), form, new CityForm());
            model.addAttribute("locationError", ex.getMessage());
            return "adminLocationView";
        }
    }

    @RequestMapping(value = "/city/save", method = RequestMethod.POST)
    public String saveCity(@Validated @ModelAttribute("cityForm") CityForm form,
            BindingResult result, Model model, HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/app/login";
        }

        if (result.hasErrors()) {
            populatePage(model, new CountryForm(), new StateForm(), form);
            return "adminLocationView";
        }

        try {
            adminLocationDaoImpl.saveCity(form);
            return "redirect:/app/admin/locations?success=1";
        } catch (Exception ex) {
            logger.error("Failed to save city: {}", ex.getMessage(), ex);
            populatePage(model, new CountryForm(), new StateForm(), form);
            model.addAttribute("locationError", ex.getMessage());
            return "adminLocationView";
        }
    }

    @RequestMapping(value = "/country/delete", method = RequestMethod.POST)
    public String deleteCountry(@RequestParam("countryId") Integer countryId,
            Model model, HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/app/login";
        }

        try {
            adminLocationDaoImpl.deleteCountry(countryId);
            return "redirect:/app/admin/locations?success=1";
        } catch (Exception ex) {
            populatePage(model, new CountryForm(), new StateForm(), new CityForm());
            model.addAttribute("locationError", ex.getMessage());
            return "adminLocationView";
        }
    }

    @RequestMapping(value = "/state/delete", method = RequestMethod.POST)
    public String deleteState(@RequestParam("stateId") Integer stateId,
            Model model, HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/app/login";
        }

        try {
            adminLocationDaoImpl.deleteState(stateId);
            return "redirect:/app/admin/locations?success=1";
        } catch (Exception ex) {
            populatePage(model, new CountryForm(), new StateForm(), new CityForm());
            model.addAttribute("locationError", ex.getMessage());
            return "adminLocationView";
        }
    }

    @RequestMapping(value = "/city/delete", method = RequestMethod.POST)
    public String deleteCity(@RequestParam("cityId") Integer cityId,
            Model model, HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/app/login";
        }

        try {
            adminLocationDaoImpl.deleteCity(cityId);
            return "redirect:/app/admin/locations?success=1";
        } catch (Exception ex) {
            populatePage(model, new CountryForm(), new StateForm(), new CityForm());
            model.addAttribute("locationError", ex.getMessage());
            return "adminLocationView";
        }
    }

    private void populatePage(Model model, CountryForm countryForm, StateForm stateForm, CityForm cityForm) {
        model.addAttribute("countryForm", countryForm);
        model.addAttribute("stateForm", stateForm);
        model.addAttribute("cityForm", cityForm);
        model.addAttribute("countries", adminLocationDaoImpl.getCountries());
        model.addAttribute("states", adminLocationDaoImpl.getStates());
        model.addAttribute("cities", adminLocationDaoImpl.getCities());
    }

    private boolean isAdmin(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUsername");
        String userType = (String) session.getAttribute("loggedInUserType");
        return username != null && userType != null && "admin".equalsIgnoreCase(userType);
    }
}
