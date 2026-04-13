/*package org.fujitsu.training.codes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.AdminUserPackageDaoImpl;
import org.fujitsu.training.codes.model.form.UserPackageForm;
import org.fujitsu.training.codes.validator.UserPackageFormValidator;
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
@RequestMapping("/admin/user-packages")
public class AdminUserPackageController {
    private static final Logger logger = LogManager.getLogger(AdminUserPackageController.class);

    private final AdminUserPackageDaoImpl adminUserPackageDaoImpl;
    private final UserPackageFormValidator userPackageFormValidator;

    public AdminUserPackageController(AdminUserPackageDaoImpl adminUserPackageDaoImpl,
            UserPackageFormValidator userPackageFormValidator) {
        this.adminUserPackageDaoImpl = adminUserPackageDaoImpl;
        this.userPackageFormValidator = userPackageFormValidator;
    }

    @InitBinder("userPackageForm")
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(userPackageFormValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String loadUserPackages(
            @RequestParam(value = "userPackageId", required = false) Integer userPackageId,
            @RequestParam(value = "success", required = false) String success,
            Model model,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/app/login";
        }

        UserPackageForm form = new UserPackageForm();
        if (userPackageId != null) {
            UserPackageForm selectedForm = adminUserPackageDaoImpl.getUserPackageForm(userPackageId);
            if (selectedForm != null) {
                form = selectedForm;
            }
        }

        model.addAttribute("userPackageForm", form);
        model.addAttribute("bidders", adminUserPackageDaoImpl.getBidders());
        model.addAttribute("packages", adminUserPackageDaoImpl.getPackages());
        model.addAttribute("userPackageInfos", adminUserPackageDaoImpl.getAllUserPackageInfos());

        if ("1".equals(success)) {
            model.addAttribute("assignSuccess", "User package assigned successfully.");
        }

        return "adminUserPackageView";
    }

    @RequestMapping(value = "/assign", method = RequestMethod.POST)
    public String assignPackage(
            @Validated @ModelAttribute("userPackageForm") UserPackageForm form,
            BindingResult result,
            Model model,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/app/login";
        }

        if (result.hasErrors()) {
            model.addAttribute("bidders", adminUserPackageDaoImpl.getBidders());
            model.addAttribute("packages", adminUserPackageDaoImpl.getPackages());
            model.addAttribute("userPackageInfos", adminUserPackageDaoImpl.getAllUserPackageInfos());
            return "adminUserPackageView";
        }

        try {
            adminUserPackageDaoImpl.assignPackage(form);
            return "redirect:/app/admin/user-packages?success=1";
        } catch (Exception ex) {
            logger.error("Failed to assign package to bidder {}: {}", form.getUsername(), ex.getMessage(), ex);
            model.addAttribute("bidders", adminUserPackageDaoImpl.getBidders());
            model.addAttribute("packages", adminUserPackageDaoImpl.getPackages());
            model.addAttribute("userPackageInfos", adminUserPackageDaoImpl.getAllUserPackageInfos());
            model.addAttribute("assignError", ex.getMessage());
            return "adminUserPackageView";
        }
    }

    private boolean isAdmin(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUsername");
        String userType = (String) session.getAttribute("loggedInUserType");
        return username != null && userType != null && "admin".equalsIgnoreCase(userType);
    }
}*/
package org.fujitsu.training.codes.controller;

import org.fujitsu.training.codes.helper.AdminUserPackageHelper;
import org.fujitsu.training.codes.helper.SessionRoleHelper;
import org.fujitsu.training.codes.model.form.PackagePurchaseRequestReviewForm;
import org.fujitsu.training.codes.model.form.UserPackageForm;
import org.fujitsu.training.codes.validator.UserPackageFormValidator;
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
@RequestMapping("/admin/user-packages")
public class AdminUserPackageController {

    private static final String LOGIN_REDIRECT = "redirect:/app/login";
    private static final String VIEW_NAME = "adminUserPackageView";

    private final AdminUserPackageHelper adminUserPackageHelper;
    private final UserPackageFormValidator userPackageFormValidator;
    private final SessionRoleHelper sessionRoleHelper;

    public AdminUserPackageController(AdminUserPackageHelper adminUserPackageHelper,
            UserPackageFormValidator userPackageFormValidator,
            SessionRoleHelper sessionRoleHelper) {
        this.adminUserPackageHelper = adminUserPackageHelper;
        this.userPackageFormValidator = userPackageFormValidator;
        this.sessionRoleHelper = sessionRoleHelper;
    }

    @InitBinder("userPackageForm")
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(userPackageFormValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String loadUserPackages(
            @RequestParam(value = "userPackageId", required = false) Integer userPackageId,
            @RequestParam(value = "success", required = false) String success,
            Model model,
            HttpSession session) {

        if (!sessionRoleHelper.isAdmin(session)) {
            return LOGIN_REDIRECT;
        }

        adminUserPackageHelper.prepareLoadUserPackages(userPackageId, success, model);
        return VIEW_NAME;
    }

    @RequestMapping(value = "/assign", method = RequestMethod.POST)
    public String assignPackage(
            @Validated @ModelAttribute("userPackageForm") UserPackageForm form,
            BindingResult result,
            Model model,
            HttpSession session) {

        if (!sessionRoleHelper.isAdmin(session)) {
            return LOGIN_REDIRECT;
        }

        return adminUserPackageHelper.processAssignPackage(form, result, model);
    }

    @RequestMapping(value = "/requests/approve", method = RequestMethod.POST)
    public String approveRequest(
            @ModelAttribute PackagePurchaseRequestReviewForm form,
            Model model,
            HttpSession session) {

        if (!sessionRoleHelper.isAdmin(session)) {
            return LOGIN_REDIRECT;
        }

        String adminUsername = sessionRoleHelper.getLoggedInUsername(session);
        return adminUserPackageHelper.processApproveRequest(form, adminUsername, model);
    }

    @RequestMapping(value = "/requests/reject", method = RequestMethod.POST)
    public String rejectRequest(
            @ModelAttribute PackagePurchaseRequestReviewForm form,
            Model model,
            HttpSession session) {

        if (!sessionRoleHelper.isAdmin(session)) {
            return LOGIN_REDIRECT;
        }

        String adminUsername = sessionRoleHelper.getLoggedInUsername(session);
        return adminUserPackageHelper.processRejectRequest(form, adminUsername, model);
    }
}

