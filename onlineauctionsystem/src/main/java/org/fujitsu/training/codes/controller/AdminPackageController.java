/*package org.fujitsu.training.codes.controller;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.AdminPackageDaoImpl;
import org.fujitsu.training.codes.model.form.PackageForm;
import org.fujitsu.training.codes.validator.PackageFormValidator;
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
@RequestMapping("/admin/packages")
public class AdminPackageController {
    private static final Logger logger = LogManager.getLogger(AdminPackageController.class);

    private final AdminPackageDaoImpl adminPackageDaoImpl;
    private final PackageFormValidator packageFormValidator;

    public AdminPackageController(AdminPackageDaoImpl adminPackageDaoImpl,
            PackageFormValidator packageFormValidator) {
        this.adminPackageDaoImpl = adminPackageDaoImpl;
        this.packageFormValidator = packageFormValidator;
    }

    @InitBinder("packageForm")
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(packageFormValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String loadPackages(
            @RequestParam(value = "success", required = false) String success,
            Model model,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/app/login";
        }

        model.addAttribute("packageForm", new PackageForm());
        model.addAttribute("packages", adminPackageDaoImpl.getAllPackages());

        if ("1".equals(success)) {
            model.addAttribute("saveSuccess", "Package saved successfully.");
        }

        return "adminPackageView";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String loadEditForm(@RequestParam("packageId") Integer packageId,
            Model model,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/app/login";
        }

        PackageForm form = adminPackageDaoImpl.getPackageForm(packageId);
        if (form == null) {
            return "redirect:/app/admin/packages";
        }

        model.addAttribute("packageForm", form);
        model.addAttribute("packages", adminPackageDaoImpl.getAllPackages());
        model.addAttribute("editMode", true);
        return "adminPackageView";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String savePackage(
            @Validated @ModelAttribute("packageForm") PackageForm form,
            BindingResult result,
            Model model,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/app/login";
        }

        if (result.hasErrors()) {
            model.addAttribute("packages", adminPackageDaoImpl.getAllPackages());
            model.addAttribute("editMode", form.getPackageId() != null);
            return "adminPackageView";
        }

        try {
            handlePackagePhoto(form);
            adminPackageDaoImpl.savePackage(form);
            return "redirect:/app/admin/packages?success=1";
        } catch (Exception ex) {
            logger.error("Failed to save package: {}", ex.getMessage(), ex);
            model.addAttribute("packages", adminPackageDaoImpl.getAllPackages());
            model.addAttribute("editMode", form.getPackageId() != null);
            model.addAttribute("saveError", ex.getMessage());
            return "adminPackageView";
        }
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deletePackage(@RequestParam("packageId") Integer packageId,
            Model model,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/app/login";
        }

        try {
            adminPackageDaoImpl.deletePackage(packageId);
            return "redirect:/app/admin/packages?success=1";
        } catch (Exception ex) {
            logger.error("Failed to delete package {}: {}", packageId, ex.getMessage(), ex);
            model.addAttribute("packageForm", new PackageForm());
            model.addAttribute("packages", adminPackageDaoImpl.getAllPackages());
            model.addAttribute("saveError", ex.getMessage());
            return "adminPackageView";
        }
    }
    
    private void handlePackagePhoto(PackageForm form) throws Exception {
        if (form.getPhotoFile() == null || form.getPhotoFile().isEmpty()) {
            form.setPhotoPath(form.getCurrentPhotoPath());
            return;
        }

        String uploadDirPath = "C:/auction_uploads/package/";
        File uploadDir = new File(uploadDirPath);

        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            throw new IllegalStateException("Failed to create package upload directory.");
        }

        String originalFilename = form.getPhotoFile().getOriginalFilename();
        String safeFilename = (originalFilename == null ? "package.jpg" : originalFilename)
                .replaceAll("[^a-zA-Z0-9._-]", "_");

        String packageKey = (form.getPackageName() == null ? "package" : form.getPackageName())
                .replaceAll("[^a-zA-Z0-9._-]", "_");

        String fileName = packageKey + "_" + System.currentTimeMillis() + "_" + safeFilename;
        File destination = new File(uploadDir, fileName);

        form.getPhotoFile().transferTo(destination);
        form.setPhotoPath("/app/package/" + fileName);
    }


    private boolean isAdmin(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUsername");
        String userType = (String) session.getAttribute("loggedInUserType");
        return username != null && userType != null && "admin".equalsIgnoreCase(userType);
    }
}*/

package org.fujitsu.training.codes.controller;

import org.fujitsu.training.codes.helper.AdminPackageHelper;
import org.fujitsu.training.codes.model.form.PackageForm;
import org.fujitsu.training.codes.validator.PackageFormValidator;
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
@RequestMapping("/admin/packages")
public class AdminPackageController {

    private static final String LOGIN_REDIRECT = "redirect:/app/login";
    private static final String VIEW_NAME = "adminPackageView";

    private final AdminPackageHelper adminPackageHelper;
    private final PackageFormValidator packageFormValidator;

    public AdminPackageController(AdminPackageHelper adminPackageHelper,
            PackageFormValidator packageFormValidator) {
        this.adminPackageHelper = adminPackageHelper;
        this.packageFormValidator = packageFormValidator;
    }

    @InitBinder("packageForm")
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(packageFormValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String loadPackages(
            @RequestParam(value = "success", required = false) String success,
            Model model,
            HttpSession session) {

        if (!isAdmin(session)) {
            return LOGIN_REDIRECT;
        }

        adminPackageHelper.prepareLoadPackages(success, model);
        return VIEW_NAME;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String loadEditForm(@RequestParam("packageId") Integer packageId,
            Model model,
            HttpSession session) {

        if (!isAdmin(session)) {
            return LOGIN_REDIRECT;
        }

        return adminPackageHelper.prepareEditForm(packageId, model);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String savePackage(
            @Validated @ModelAttribute("packageForm") PackageForm form,
            BindingResult result,
            Model model,
            HttpSession session) {

        if (!isAdmin(session)) {
            return LOGIN_REDIRECT;
        }

        return adminPackageHelper.processSavePackage(form, result, model);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deletePackage(@RequestParam("packageId") Integer packageId,
            Model model,
            HttpSession session) {

        if (!isAdmin(session)) {
            return LOGIN_REDIRECT;
        }

        return adminPackageHelper.processDeletePackage(packageId, model);
    }

    private boolean isAdmin(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUsername");
        String userType = (String) session.getAttribute("loggedInUserType");
        return username != null && userType != null && "admin".equalsIgnoreCase(userType);
    }
}

