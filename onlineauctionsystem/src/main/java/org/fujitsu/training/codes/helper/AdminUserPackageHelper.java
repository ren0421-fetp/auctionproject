/*package org.fujitsu.training.codes.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.AdminUserPackageDaoImpl;
import org.fujitsu.training.codes.model.form.UserPackageForm;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Component
public class AdminUserPackageHelper {
    private static final Logger logger = LogManager.getLogger(AdminUserPackageHelper.class);

    private static final String VIEW_NAME = "adminUserPackageView";
    private static final String SUCCESS_REDIRECT = "redirect:/app/admin/user-packages?success=1";

    private final AdminUserPackageDaoImpl adminUserPackageDaoImpl;

    public AdminUserPackageHelper(AdminUserPackageDaoImpl adminUserPackageDaoImpl) {
        this.adminUserPackageDaoImpl = adminUserPackageDaoImpl;
    }

    public void prepareLoadUserPackages(Integer userPackageId, String success, Model model) {
        UserPackageForm form = resolveUserPackageForm(userPackageId);

        model.addAttribute("userPackageForm", form);
        model.addAttribute("bidders", adminUserPackageDaoImpl.getBidders());
        model.addAttribute("packages", adminUserPackageDaoImpl.getPackages());
        model.addAttribute("userPackageInfos", adminUserPackageDaoImpl.getAllUserPackageInfos());

        if ("1".equals(success)) {
            model.addAttribute("assignSuccess", "User package assigned successfully.");
        }
    }

    public String processAssignPackage(UserPackageForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            populatePage(model, form);
            return VIEW_NAME;
        }

        try {
            adminUserPackageDaoImpl.assignPackage(form);
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Failed to assign package to bidder {}: {}", form.getUsername(), ex.getMessage(), ex);
            populatePage(model, form);
            model.addAttribute("assignError", ex.getMessage());
            return VIEW_NAME;
        }
    }

    private UserPackageForm resolveUserPackageForm(Integer userPackageId) {
        if (userPackageId == null) {
            return new UserPackageForm();
        }

        UserPackageForm form = adminUserPackageDaoImpl.getUserPackageForm(userPackageId);
        return form == null ? new UserPackageForm() : form;
    }

    private void populatePage(Model model, UserPackageForm form) {
        model.addAttribute("userPackageForm", form);
        model.addAttribute("bidders", adminUserPackageDaoImpl.getBidders());
        model.addAttribute("packages", adminUserPackageDaoImpl.getPackages());
        model.addAttribute("userPackageInfos", adminUserPackageDaoImpl.getAllUserPackageInfos());
    }
}
*/

package org.fujitsu.training.codes.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.AdminUserPackageDaoImpl;
import org.fujitsu.training.codes.model.form.UserPackageForm;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Component
public class AdminUserPackageHelper {
    private static final Logger logger = LogManager.getLogger("admin-flow");

    private static final String VIEW_NAME = "adminUserPackageView";
    private static final String SUCCESS_REDIRECT = "redirect:/app/admin/user-packages?success=1";

    private final AdminUserPackageDaoImpl adminUserPackageDaoImpl;

    public AdminUserPackageHelper(AdminUserPackageDaoImpl adminUserPackageDaoImpl) {
        this.adminUserPackageDaoImpl = adminUserPackageDaoImpl;
    }

    public void prepareLoadUserPackages(Integer userPackageId, String success, Model model) {
        logger.info("Loading admin user-package page. userPackageId={}", userPackageId);

        UserPackageForm form = resolveUserPackageForm(userPackageId);
        model.addAttribute("userPackageForm", form);
        model.addAttribute("bidders", adminUserPackageDaoImpl.getBidders());
        model.addAttribute("packages", adminUserPackageDaoImpl.getPackages());
        model.addAttribute("userPackageInfos", adminUserPackageDaoImpl.getAllUserPackageInfos());

        if ("1".equals(success)) {
            model.addAttribute("assignSuccess", "User package assigned successfully.");
        }

        logger.info("Admin user-package page loaded. userPackageId={}", userPackageId);
    }

    public String processAssignPackage(UserPackageForm form, BindingResult result, Model model) {
        logger.info("Processing user-package assignment. username={}, packageId={}",
                form.getUsername(), form.getPackageId());

        if (result.hasErrors()) {
            populatePage(model, form);
            logger.warn("User-package validation failed. username={}, packageId={}",
                    form.getUsername(), form.getPackageId());
            return VIEW_NAME;
        }

        try {
            adminUserPackageDaoImpl.assignPackage(form);
            logger.info("User-package assignment completed. username={}, packageId={}",
                    form.getUsername(), form.getPackageId());
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Failed to assign package to bidder {}: {}", form.getUsername(), ex.getMessage(), ex);
            populatePage(model, form);
            model.addAttribute("assignError", ex.getMessage());
            return VIEW_NAME;
        }
    }

    private UserPackageForm resolveUserPackageForm(Integer userPackageId) {
        if (userPackageId == null) {
            return new UserPackageForm();
        }

        UserPackageForm form = adminUserPackageDaoImpl.getUserPackageForm(userPackageId);
        return form == null ? new UserPackageForm() : form;
    }

    private void populatePage(Model model, UserPackageForm form) {
        model.addAttribute("userPackageForm", form);
        model.addAttribute("bidders", adminUserPackageDaoImpl.getBidders());
        model.addAttribute("packages", adminUserPackageDaoImpl.getPackages());
        model.addAttribute("userPackageInfos", adminUserPackageDaoImpl.getAllUserPackageInfos());
    }
}
