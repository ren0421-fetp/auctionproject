/*package org.fujitsu.training.codes.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.SellerProfileDaoImpl;
import org.fujitsu.training.codes.model.form.ChangePasswordForm;
import org.fujitsu.training.codes.model.form.SellerProfileForm;
import org.fujitsu.training.codes.service.FileService;
import org.fujitsu.training.codes.validator.ChangePasswordFormValidator;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

@Component
public class SellerProfileHelper {
    private static final Logger logger = LogManager.getLogger(SellerProfileHelper.class);

    private static final String VIEW_NAME = "sellerProfileView";
    private static final String LOGIN_REDIRECT = "redirect:/app/login";

    private final SellerProfileDaoImpl sellerProfileDaoImpl;
    private final FileService fileService;
    private final ChangePasswordFormValidator changePasswordFormValidator;

    public SellerProfileHelper(SellerProfileDaoImpl sellerProfileDaoImpl,
            FileService fileService,
            ChangePasswordFormValidator changePasswordFormValidator) {
        this.sellerProfileDaoImpl = sellerProfileDaoImpl;
        this.fileService = fileService;
        this.changePasswordFormValidator = changePasswordFormValidator;
    }

    public String prepareLoadProfile(String username, Model model) {
        SellerProfileForm form = sellerProfileDaoImpl.getSellerProfileForm(username);
        if (form == null) {
            return LOGIN_REDIRECT;
        }

        populateProfilePage(model, form, new ChangePasswordForm());
        return VIEW_NAME;
    }

    public String processUpdateProfile(SellerProfileForm form,
            BindingResult result,
            Model model,
            String username) {

        ChangePasswordForm passwordForm = new ChangePasswordForm();

        if (result.hasErrors()) {
            populateProfilePage(model, form, passwordForm);
            return VIEW_NAME;
        }

        try {
            handleProfilePhoto(form, username);
            sellerProfileDaoImpl.updateSellerProfile(form, username);
            model.addAttribute("profileSuccess", "Profile updated successfully.");
        } catch (Exception ex) {
            logger.error("Failed to update seller profile for {}: {}", username, ex.getMessage(), ex);
            model.addAttribute("profileError", ex.getMessage());
        }

        populateProfilePage(model, sellerProfileDaoImpl.getSellerProfileForm(username), passwordForm);
        return VIEW_NAME;
    }

    public String processChangePassword(ChangePasswordForm form,
            Model model,
            String username) {

        BindingResult passwordResult = new BeanPropertyBindingResult(form, "changePasswordForm");
        changePasswordFormValidator.validate(form, passwordResult);

        SellerProfileForm profileForm = sellerProfileDaoImpl.getSellerProfileForm(username);
        if (profileForm == null) {
            return LOGIN_REDIRECT;
        }

        if (passwordResult.hasErrors()) {
            model.addAttribute("org.springframework.validation.BindingResult.changePasswordForm", passwordResult);
            populateProfilePage(model, profileForm, form);
            return VIEW_NAME;
        }

        try {
            sellerProfileDaoImpl.changeSellerPassword(form, username);
            model.addAttribute("passwordSuccess", "Password changed successfully.");
            populateProfilePage(model, profileForm, new ChangePasswordForm());
        } catch (Exception ex) {
            logger.error("Failed to change seller password for {}: {}", username, ex.getMessage(), ex);
            model.addAttribute("passwordError", ex.getMessage());
            populateProfilePage(model, profileForm, form);
        }

        return VIEW_NAME;
    }

    private void populateProfilePage(Model model, SellerProfileForm profileForm, ChangePasswordForm changePasswordForm) {
        model.addAttribute("sellerProfileForm", profileForm);
        model.addAttribute("changePasswordForm", changePasswordForm);
        model.addAttribute("countryOpts", sellerProfileDaoImpl.getCountries());
        model.addAttribute("stateOpts", sellerProfileDaoImpl.getStates());
        model.addAttribute("cityOpts", sellerProfileDaoImpl.getCities());
    }

    private void handleProfilePhoto(SellerProfileForm form, String username) throws Exception {
        if (form.getPhotoFile() == null || form.getPhotoFile().isEmpty()) {
            form.setPhotoPath(form.getCurrentPhotoPath());
            return;
        }

        String photoPath = fileService.saveFile(form.getPhotoFile(), "profile", username);
        form.setPhotoPath(photoPath);
    }
}
*/

package org.fujitsu.training.codes.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.SellerProfileDaoImpl;
import org.fujitsu.training.codes.model.form.ChangePasswordForm;
import org.fujitsu.training.codes.model.form.SellerProfileForm;
import org.fujitsu.training.codes.service.FileService;
import org.fujitsu.training.codes.validator.ChangePasswordFormValidator;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

@Component
public class SellerProfileHelper {
    private static final Logger logger = LogManager.getLogger("seller-flow");

    private static final String VIEW_NAME = "sellerProfileView";
    private static final String LOGIN_REDIRECT = "redirect:/app/login";

    private final SellerProfileDaoImpl sellerProfileDaoImpl;
    private final FileService fileService;
    private final ChangePasswordFormValidator changePasswordFormValidator;

    public SellerProfileHelper(SellerProfileDaoImpl sellerProfileDaoImpl,
            FileService fileService,
            ChangePasswordFormValidator changePasswordFormValidator) {
        this.sellerProfileDaoImpl = sellerProfileDaoImpl;
        this.fileService = fileService;
        this.changePasswordFormValidator = changePasswordFormValidator;
    }

    public String prepareLoadProfile(String username, Model model) {
        logger.info("Loading seller profile page. username={}", username);

        SellerProfileForm form = sellerProfileDaoImpl.getSellerProfileForm(username);
        if (form == null) {
            logger.warn("Seller profile load failed. username={} not found.", username);
            return LOGIN_REDIRECT;
        }

        populateProfilePage(model, form, new ChangePasswordForm());
        logger.info("Seller profile page loaded. username={}", username);
        return VIEW_NAME;
    }

    public String processUpdateProfile(SellerProfileForm form,
            BindingResult result,
            Model model,
            String username) {
        logger.info("Processing seller profile update. username={}", username);

        ChangePasswordForm passwordForm = new ChangePasswordForm();

        if (result.hasErrors()) {
            populateProfilePage(model, form, passwordForm);
            logger.warn("Seller profile validation failed. username={}", username);
            return VIEW_NAME;
        }

        try {
            handleProfilePhoto(form, username);
            sellerProfileDaoImpl.updateSellerProfile(form, username);
            model.addAttribute("profileSuccess", "Profile updated successfully.");
            logger.info("Seller profile update completed. username={}", username);
        } catch (Exception ex) {
            logger.error("Failed to update seller profile for {}: {}", username, ex.getMessage(), ex);
            model.addAttribute("profileError", ex.getMessage());
        }

        populateProfilePage(model, sellerProfileDaoImpl.getSellerProfileForm(username), passwordForm);
        return VIEW_NAME;
    }

    public String processChangePassword(ChangePasswordForm form,
            Model model,
            String username) {
        logger.info("Processing seller password change. username={}", username);

        BindingResult passwordResult = new BeanPropertyBindingResult(form, "changePasswordForm");
        changePasswordFormValidator.validate(form, passwordResult);

        SellerProfileForm profileForm = sellerProfileDaoImpl.getSellerProfileForm(username);
        if (profileForm == null) {
            logger.warn("Seller password change failed. username={} profile not found.", username);
            return LOGIN_REDIRECT;
        }

        if (passwordResult.hasErrors()) {
            model.addAttribute("org.springframework.validation.BindingResult.changePasswordForm", passwordResult);
            populateProfilePage(model, profileForm, form);
            logger.warn("Seller password validation failed. username={}", username);
            return VIEW_NAME;
        }

        try {
            sellerProfileDaoImpl.changeSellerPassword(form, username);
            model.addAttribute("passwordSuccess", "Password changed successfully.");
            populateProfilePage(model, profileForm, new ChangePasswordForm());
            logger.info("Seller password change completed. username={}", username);
        } catch (Exception ex) {
            logger.error("Failed to change seller password for {}: {}", username, ex.getMessage(), ex);
            model.addAttribute("passwordError", ex.getMessage());
            populateProfilePage(model, profileForm, form);
        }

        return VIEW_NAME;
    }

    private void populateProfilePage(Model model, SellerProfileForm profileForm, ChangePasswordForm changePasswordForm) {
        model.addAttribute("sellerProfileForm", profileForm);
        model.addAttribute("changePasswordForm", changePasswordForm);
        model.addAttribute("countryOpts", sellerProfileDaoImpl.getCountries());
        model.addAttribute("stateOpts", sellerProfileDaoImpl.getStates());
        model.addAttribute("cityOpts", sellerProfileDaoImpl.getCities());
    }

    private void handleProfilePhoto(SellerProfileForm form, String username) throws Exception {
        if (form.getPhotoFile() == null || form.getPhotoFile().isEmpty()) {
            form.setPhotoPath(form.getCurrentPhotoPath());
            return;
        }

        String photoPath = fileService.saveFile(form.getPhotoFile(), "profile", username);
        form.setPhotoPath(photoPath);
    }
}
