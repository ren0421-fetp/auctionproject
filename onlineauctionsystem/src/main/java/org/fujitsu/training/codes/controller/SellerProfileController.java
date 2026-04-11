package org.fujitsu.training.codes.controller;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.SellerProfileDaoImpl;
import org.fujitsu.training.codes.model.form.ChangePasswordForm;
import org.fujitsu.training.codes.model.form.SellerProfileForm;
import org.fujitsu.training.codes.validator.ChangePasswordFormValidator;
import org.fujitsu.training.codes.validator.SellerProfileFormValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BeanPropertyBindingResult;
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
    private static final Logger logger = LogManager.getLogger(SellerProfileController.class);

    private final SellerProfileDaoImpl sellerProfileDaoImpl;
    private final SellerProfileFormValidator sellerProfileFormValidator;
    private final ChangePasswordFormValidator changePasswordFormValidator;

    public SellerProfileController(SellerProfileDaoImpl sellerProfileDaoImpl,
            SellerProfileFormValidator sellerProfileFormValidator,
            ChangePasswordFormValidator changePasswordFormValidator) {
        this.sellerProfileDaoImpl = sellerProfileDaoImpl;
        this.sellerProfileFormValidator = sellerProfileFormValidator;
        this.changePasswordFormValidator = changePasswordFormValidator;
    }

    @InitBinder("sellerProfileForm")
    public void initProfileBinder(WebDataBinder binder) {
        binder.setValidator(sellerProfileFormValidator);
    }

    @InitBinder("changePasswordForm")
    public void initPasswordBinder(WebDataBinder binder) {
        binder.setValidator(changePasswordFormValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String loadProfile(Model model, HttpSession session) {
        String username = getSellerUsername(session);
        if (username == null) {
            return "redirect:/app/login";
        }

        SellerProfileForm form = sellerProfileDaoImpl.getSellerProfileForm(username);
        if (form == null) {
            return "redirect:/app/login";
        }

        model.addAttribute("sellerProfileForm", form);
        model.addAttribute("changePasswordForm", new ChangePasswordForm());
        populateReferenceData(model);
        return "sellerProfileView";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateProfile(
            @Validated @ModelAttribute("sellerProfileForm") SellerProfileForm form,
            BindingResult result,
            Model model,
            HttpSession session) {

        String username = getSellerUsername(session);
        if (username == null) {
            return "redirect:/app/login";
        }

        ChangePasswordForm passwordForm = new ChangePasswordForm();
        model.addAttribute("changePasswordForm", passwordForm);

        if (result.hasErrors()) {
            populateReferenceData(model);
            return "sellerProfileView";
        }

        try {
            handleProfilePhoto(form, username);
            sellerProfileDaoImpl.updateSellerProfile(form, username);
            model.addAttribute("profileSuccess", "Profile updated successfully.");
        } catch (Exception ex) {
            logger.error("Failed to update seller profile for {}: {}", username, ex.getMessage(), ex);
            model.addAttribute("profileError", ex.getMessage());
        }

        model.addAttribute("sellerProfileForm", sellerProfileDaoImpl.getSellerProfileForm(username));
        populateReferenceData(model);
        return "sellerProfileView";
    }

    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
    public String changePassword(
            @ModelAttribute("changePasswordForm") ChangePasswordForm form,
            Model model,
            HttpSession session) {

        String username = getSellerUsername(session);
        if (username == null) {
            return "redirect:/app/login";
        }

        BindingResult passwordResult = new BeanPropertyBindingResult(form, "changePasswordForm");
        changePasswordFormValidator.validate(form, passwordResult);

        SellerProfileForm profileForm = sellerProfileDaoImpl.getSellerProfileForm(username);
        model.addAttribute("sellerProfileForm", profileForm);

        if (passwordResult.hasErrors()) {
            model.addAttribute("org.springframework.validation.BindingResult.changePasswordForm", passwordResult);
            model.addAttribute("changePasswordForm", form);
            populateReferenceData(model);
            return "sellerProfileView";
        }

        try {
            sellerProfileDaoImpl.changeSellerPassword(form, username);
            model.addAttribute("passwordSuccess", "Password changed successfully.");
            model.addAttribute("changePasswordForm", new ChangePasswordForm());
        } catch (Exception ex) {
            logger.error("Failed to change seller password for {}: {}", username, ex.getMessage(), ex);
            model.addAttribute("passwordError", ex.getMessage());
            model.addAttribute("changePasswordForm", form);
        }

        populateReferenceData(model);
        return "sellerProfileView";
    }

    private String getSellerUsername(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUsername");
        String userType = (String) session.getAttribute("loggedInUserType");

        if (username == null || userType == null || !"seller".equalsIgnoreCase(userType)) {
            return null;
        }
        return username;
    }

    private void populateReferenceData(Model model) {
        model.addAttribute("countryOpts", sellerProfileDaoImpl.getCountries());
        model.addAttribute("stateOpts", sellerProfileDaoImpl.getStates());
        model.addAttribute("cityOpts", sellerProfileDaoImpl.getCities());
    }

    private void handleProfilePhoto(SellerProfileForm form, String username) throws Exception {
        if (form.getPhotoFile() == null || form.getPhotoFile().isEmpty()) {
            form.setPhotoPath(form.getCurrentPhotoPath());
            return;
        }

        String uploadPath = "C:/auction_uploads/profile/";
        File dir = new File(uploadPath);

        if (!dir.exists() && !dir.mkdirs()) {
            throw new IllegalStateException("Failed to create seller profile upload directory.");
        }

        String originalFilename = form.getPhotoFile().getOriginalFilename();
        String safeFilename = (originalFilename == null ? "profile.jpg" : originalFilename)
                .replaceAll("[^a-zA-Z0-9._-]", "_");

        String fileName = username + "_" + System.currentTimeMillis() + "_" + safeFilename;
        File destination = new File(dir, fileName);

        form.getPhotoFile().transferTo(destination);
        form.setPhotoPath("/app/profile/" + fileName);
    }
}
