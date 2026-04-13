/*package org.fujitsu.training.codes.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.AdminLocationDaoImpl;
import org.fujitsu.training.codes.model.form.CityForm;
import org.fujitsu.training.codes.model.form.CountryForm;
import org.fujitsu.training.codes.model.form.StateForm;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Component
public class AdminLocationHelper {
    private static final Logger logger = LogManager.getLogger(AdminLocationHelper.class);

    private static final String VIEW_NAME = "adminLocationView";
    private static final String SUCCESS_REDIRECT = "redirect:/app/admin/locations?success=1";

    private final AdminLocationDaoImpl adminLocationDaoImpl;

    public AdminLocationHelper(AdminLocationDaoImpl adminLocationDaoImpl) {
        this.adminLocationDaoImpl = adminLocationDaoImpl;
    }

    public void prepareLoadPage(Integer countryId, Integer stateId, Integer cityId, String success, Model model) {
        CountryForm countryForm = resolveCountryForm(countryId);
        StateForm stateForm = resolveStateForm(stateId);
        CityForm cityForm = resolveCityForm(cityId);

        populatePage(model, countryForm, stateForm, cityForm);

        if ("1".equals(success)) {
            model.addAttribute("locationSuccess", "Location data saved successfully.");
        }
    }

    public String processSaveCountry(CountryForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            populatePage(model, form, new StateForm(), new CityForm());
            return VIEW_NAME;
        }

        try {
            adminLocationDaoImpl.saveCountry(form);
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Failed to save country: {}", ex.getMessage(), ex);
            populatePage(model, form, new StateForm(), new CityForm());
            model.addAttribute("locationError", ex.getMessage());
            return VIEW_NAME;
        }
    }

    public String processSaveState(StateForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            populatePage(model, new CountryForm(), form, new CityForm());
            return VIEW_NAME;
        }

        try {
            adminLocationDaoImpl.saveState(form);
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Failed to save state: {}", ex.getMessage(), ex);
            populatePage(model, new CountryForm(), form, new CityForm());
            model.addAttribute("locationError", ex.getMessage());
            return VIEW_NAME;
        }
    }

    public String processSaveCity(CityForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            populatePage(model, new CountryForm(), new StateForm(), form);
            return VIEW_NAME;
        }

        try {
            adminLocationDaoImpl.saveCity(form);
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Failed to save city: {}", ex.getMessage(), ex);
            populatePage(model, new CountryForm(), new StateForm(), form);
            model.addAttribute("locationError", ex.getMessage());
            return VIEW_NAME;
        }
    }

    public String processDeleteCountry(Integer countryId, Model model) {
        try {
            adminLocationDaoImpl.deleteCountry(countryId);
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            populatePage(model, new CountryForm(), new StateForm(), new CityForm());
            model.addAttribute("locationError", ex.getMessage());
            return VIEW_NAME;
        }
    }

    public String processDeleteState(Integer stateId, Model model) {
        try {
            adminLocationDaoImpl.deleteState(stateId);
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            populatePage(model, new CountryForm(), new StateForm(), new CityForm());
            model.addAttribute("locationError", ex.getMessage());
            return VIEW_NAME;
        }
    }

    public String processDeleteCity(Integer cityId, Model model) {
        try {
            adminLocationDaoImpl.deleteCity(cityId);
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            populatePage(model, new CountryForm(), new StateForm(), new CityForm());
            model.addAttribute("locationError", ex.getMessage());
            return VIEW_NAME;
        }
    }

    private CountryForm resolveCountryForm(Integer countryId) {
        if (countryId == null) {
            return new CountryForm();
        }

        CountryForm form = adminLocationDaoImpl.getCountryForm(countryId);
        return form == null ? new CountryForm() : form;
    }

    private StateForm resolveStateForm(Integer stateId) {
        if (stateId == null) {
            return new StateForm();
        }

        StateForm form = adminLocationDaoImpl.getStateForm(stateId);
        return form == null ? new StateForm() : form;
    }

    private CityForm resolveCityForm(Integer cityId) {
        if (cityId == null) {
            return new CityForm();
        }

        CityForm form = adminLocationDaoImpl.getCityForm(cityId);
        return form == null ? new CityForm() : form;
    }

    private void populatePage(Model model, CountryForm countryForm, StateForm stateForm, CityForm cityForm) {
        model.addAttribute("countryForm", countryForm);
        model.addAttribute("stateForm", stateForm);
        model.addAttribute("cityForm", cityForm);
        model.addAttribute("countries", adminLocationDaoImpl.getCountries());
        model.addAttribute("states", adminLocationDaoImpl.getStates());
        model.addAttribute("cities", adminLocationDaoImpl.getCities());
    }
}
*/

package org.fujitsu.training.codes.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.AdminLocationDaoImpl;
import org.fujitsu.training.codes.model.form.CityForm;
import org.fujitsu.training.codes.model.form.CountryForm;
import org.fujitsu.training.codes.model.form.StateForm;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Component
public class AdminLocationHelper {
    private static final Logger logger = LogManager.getLogger("admin-flow");

    private static final String VIEW_NAME = "adminLocationView";
    private static final String SUCCESS_REDIRECT = "redirect:/app/admin/locations?success=1";

    private final AdminLocationDaoImpl adminLocationDaoImpl;

    public AdminLocationHelper(AdminLocationDaoImpl adminLocationDaoImpl) {
        this.adminLocationDaoImpl = adminLocationDaoImpl;
    }

    public void prepareLoadPage(Integer countryId, Integer stateId, Integer cityId, String success, Model model) {
        logger.info("Loading admin location page. countryId={}, stateId={}, cityId={}",
                countryId, stateId, cityId);

        CountryForm countryForm = resolveCountryForm(countryId);
        StateForm stateForm = resolveStateForm(stateId);
        CityForm cityForm = resolveCityForm(cityId);

        populatePage(model, countryForm, stateForm, cityForm);

        if ("1".equals(success)) {
            model.addAttribute("locationSuccess", "Location data saved successfully.");
        }

        logger.info("Admin location page loaded.");
    }

    public String processSaveCountry(CountryForm form, BindingResult result, Model model) {
        logger.info("Processing country save. countryId={}, countryName={}",
                form.getCountryId(), form.getCountryName());

        if (result.hasErrors()) {
            populatePage(model, form, new StateForm(), new CityForm());
            logger.warn("Country validation failed. countryId={}, countryName={}",
                    form.getCountryId(), form.getCountryName());
            return VIEW_NAME;
        }

        try {
            adminLocationDaoImpl.saveCountry(form);
            logger.info("Country save completed. countryId={}, countryName={}",
                    form.getCountryId(), form.getCountryName());
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Failed to save country: {}", ex.getMessage(), ex);
            populatePage(model, form, new StateForm(), new CityForm());
            model.addAttribute("locationError", ex.getMessage());
            return VIEW_NAME;
        }
    }

    public String processSaveState(StateForm form, BindingResult result, Model model) {
        logger.info("Processing state save. stateId={}, stateName={}",
                form.getStateId(), form.getStateName());

        if (result.hasErrors()) {
            populatePage(model, new CountryForm(), form, new CityForm());
            logger.warn("State validation failed. stateId={}, stateName={}",
                    form.getStateId(), form.getStateName());
            return VIEW_NAME;
        }

        try {
            adminLocationDaoImpl.saveState(form);
            logger.info("State save completed. stateId={}, stateName={}",
                    form.getStateId(), form.getStateName());
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Failed to save state: {}", ex.getMessage(), ex);
            populatePage(model, new CountryForm(), form, new CityForm());
            model.addAttribute("locationError", ex.getMessage());
            return VIEW_NAME;
        }
    }

    public String processSaveCity(CityForm form, BindingResult result, Model model) {
        logger.info("Processing city save. cityId={}, cityName={}",
                form.getCityId(), form.getCityName());

        if (result.hasErrors()) {
            populatePage(model, new CountryForm(), new StateForm(), form);
            logger.warn("City validation failed. cityId={}, cityName={}",
                    form.getCityId(), form.getCityName());
            return VIEW_NAME;
        }

        try {
            adminLocationDaoImpl.saveCity(form);
            logger.info("City save completed. cityId={}, cityName={}",
                    form.getCityId(), form.getCityName());
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Failed to save city: {}", ex.getMessage(), ex);
            populatePage(model, new CountryForm(), new StateForm(), form);
            model.addAttribute("locationError", ex.getMessage());
            return VIEW_NAME;
        }
    }

    public String processDeleteCountry(Integer countryId, Model model) {
        logger.info("Processing country delete. countryId={}", countryId);
        try {
            adminLocationDaoImpl.deleteCountry(countryId);
            logger.info("Country delete completed. countryId={}", countryId);
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Failed to delete country {}: {}", countryId, ex.getMessage(), ex);
            populatePage(model, new CountryForm(), new StateForm(), new CityForm());
            model.addAttribute("locationError", ex.getMessage());
            return VIEW_NAME;
        }
    }

    public String processDeleteState(Integer stateId, Model model) {
        logger.info("Processing state delete. stateId={}", stateId);
        try {
            adminLocationDaoImpl.deleteState(stateId);
            logger.info("State delete completed. stateId={}", stateId);
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Failed to delete state {}: {}", stateId, ex.getMessage(), ex);
            populatePage(model, new CountryForm(), new StateForm(), new CityForm());
            model.addAttribute("locationError", ex.getMessage());
            return VIEW_NAME;
        }
    }

    public String processDeleteCity(Integer cityId, Model model) {
        logger.info("Processing city delete. cityId={}", cityId);
        try {
            adminLocationDaoImpl.deleteCity(cityId);
            logger.info("City delete completed. cityId={}", cityId);
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Failed to delete city {}: {}", cityId, ex.getMessage(), ex);
            populatePage(model, new CountryForm(), new StateForm(), new CityForm());
            model.addAttribute("locationError", ex.getMessage());
            return VIEW_NAME;
        }
    }

    private CountryForm resolveCountryForm(Integer countryId) {
        if (countryId == null) {
            return new CountryForm();
        }

        CountryForm form = adminLocationDaoImpl.getCountryForm(countryId);
        return form == null ? new CountryForm() : form;
    }

    private StateForm resolveStateForm(Integer stateId) {
        if (stateId == null) {
            return new StateForm();
        }

        StateForm form = adminLocationDaoImpl.getStateForm(stateId);
        return form == null ? new StateForm() : form;
    }

    private CityForm resolveCityForm(Integer cityId) {
        if (cityId == null) {
            return new CityForm();
        }

        CityForm form = adminLocationDaoImpl.getCityForm(cityId);
        return form == null ? new CityForm() : form;
    }

    private void populatePage(Model model, CountryForm countryForm, StateForm stateForm, CityForm cityForm) {
        model.addAttribute("countryForm", countryForm);
        model.addAttribute("stateForm", stateForm);
        model.addAttribute("cityForm", cityForm);
        model.addAttribute("countries", adminLocationDaoImpl.getCountries());
        model.addAttribute("states", adminLocationDaoImpl.getStates());
        model.addAttribute("cities", adminLocationDaoImpl.getCities());
    }
}
