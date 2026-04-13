/*package org.fujitsu.training.codes.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.AdminPackageDaoImpl;
import org.fujitsu.training.codes.model.form.PackageForm;
import org.fujitsu.training.codes.service.FileService;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Component
public class AdminPackageHelper {
    private static final Logger logger = LogManager.getLogger(AdminPackageHelper.class);

    private static final String VIEW_NAME = "adminPackageView";
    private static final String SUCCESS_REDIRECT = "redirect:/app/admin/packages?success=1";
    private static final String PACKAGE_REDIRECT = "redirect:/app/admin/packages";

    private final AdminPackageDaoImpl adminPackageDaoImpl;
    private final FileService fileService;

    public AdminPackageHelper(AdminPackageDaoImpl adminPackageDaoImpl,
            FileService fileService) {
        this.adminPackageDaoImpl = adminPackageDaoImpl;
        this.fileService = fileService;
    }

    public void prepareLoadPackages(String success, Model model) {
        model.addAttribute("packageForm", new PackageForm());
        model.addAttribute("packages", adminPackageDaoImpl.getAllPackages());

        if ("1".equals(success)) {
            model.addAttribute("saveSuccess", "Package saved successfully.");
        }
    }

    public String prepareEditForm(Integer packageId, Model model) {
        PackageForm form = adminPackageDaoImpl.getPackageForm(packageId);
        if (form == null) {
            return PACKAGE_REDIRECT;
        }

        populatePage(model, form);
        return VIEW_NAME;
    }

    public String processSavePackage(PackageForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            populatePage(model, form);
            return VIEW_NAME;
        }

        try {
            handlePackagePhoto(form);
            adminPackageDaoImpl.savePackage(form);
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Failed to save package: {}", ex.getMessage(), ex);
            populatePage(model, form);
            model.addAttribute("saveError", ex.getMessage());
            return VIEW_NAME;
        }
    }

    public String processDeletePackage(Integer packageId, Model model) {
        try {
            adminPackageDaoImpl.deletePackage(packageId);
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Failed to delete package {}: {}", packageId, ex.getMessage(), ex);
            model.addAttribute("packageForm", new PackageForm());
            model.addAttribute("packages", adminPackageDaoImpl.getAllPackages());
            model.addAttribute("saveError", ex.getMessage());
            return VIEW_NAME;
        }
    }

    private void populatePage(Model model, PackageForm form) {
        model.addAttribute("packageForm", form);
        model.addAttribute("packages", adminPackageDaoImpl.getAllPackages());
        model.addAttribute("editMode", form.getPackageId() != null);
    }

    private void handlePackagePhoto(PackageForm form) throws Exception {
        if (form.getPhotoFile() == null || form.getPhotoFile().isEmpty()) {
            form.setPhotoPath(form.getCurrentPhotoPath());
            return;
        }

        String packageKey = (form.getPackageName() == null ? "package" : form.getPackageName())
                .replaceAll("[^a-zA-Z0-9._-]", "_");

        String photoPath = fileService.saveFile(form.getPhotoFile(), "package", packageKey);
        form.setPhotoPath(photoPath);
    }
}
*/

package org.fujitsu.training.codes.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.AdminPackageDaoImpl;
import org.fujitsu.training.codes.model.form.PackageForm;
import org.fujitsu.training.codes.service.FileService;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Component
public class AdminPackageHelper {
    private static final Logger logger = LogManager.getLogger("admin-flow");

    private static final String VIEW_NAME = "adminPackageView";
    private static final String SUCCESS_REDIRECT = "redirect:/app/admin/packages?success=1";
    private static final String PACKAGE_REDIRECT = "redirect:/app/admin/packages";

    private final AdminPackageDaoImpl adminPackageDaoImpl;
    private final FileService fileService;

    public AdminPackageHelper(AdminPackageDaoImpl adminPackageDaoImpl,
            FileService fileService) {
        this.adminPackageDaoImpl = adminPackageDaoImpl;
        this.fileService = fileService;
    }

    public void prepareLoadPackages(String success, Model model) {
        logger.info("Loading admin package page.");
        model.addAttribute("packageForm", new PackageForm());
        model.addAttribute("packages", adminPackageDaoImpl.getAllPackages());

        if ("1".equals(success)) {
            model.addAttribute("saveSuccess", "Package saved successfully.");
        }
        logger.info("Admin package page loaded.");
    }

    public String prepareEditForm(Integer packageId, Model model) {
        logger.info("Loading admin package edit form. packageId={}", packageId);
        PackageForm form = adminPackageDaoImpl.getPackageForm(packageId);
        if (form == null) {
            logger.warn("Admin package edit form load failed. packageId={} not found.", packageId);
            return PACKAGE_REDIRECT;
        }

        populatePage(model, form);
        logger.info("Admin package edit form loaded. packageId={}", packageId);
        return VIEW_NAME;
    }

    public String processSavePackage(PackageForm form, BindingResult result, Model model) {
        logger.info("Processing admin package save. packageId={}, packageName={}",
                form.getPackageId(), form.getPackageName());

        if (result.hasErrors()) {
            populatePage(model, form);
            logger.warn("Admin package validation failed. packageId={}, packageName={}",
                    form.getPackageId(), form.getPackageName());
            return VIEW_NAME;
        }

        try {
            handlePackagePhoto(form);
            adminPackageDaoImpl.savePackage(form);
            logger.info("Admin package save completed. packageId={}, packageName={}",
                    form.getPackageId(), form.getPackageName());
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Failed to save package: {}", ex.getMessage(), ex);
            populatePage(model, form);
            model.addAttribute("saveError", ex.getMessage());
            return VIEW_NAME;
        }
    }

    public String processDeletePackage(Integer packageId, Model model) {
        logger.info("Processing admin package delete. packageId={}", packageId);
        try {
            adminPackageDaoImpl.deletePackage(packageId);
            logger.info("Admin package delete completed. packageId={}", packageId);
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Failed to delete package {}: {}", packageId, ex.getMessage(), ex);
            model.addAttribute("packageForm", new PackageForm());
            model.addAttribute("packages", adminPackageDaoImpl.getAllPackages());
            model.addAttribute("saveError", ex.getMessage());
            return VIEW_NAME;
        }
    }

    private void populatePage(Model model, PackageForm form) {
        model.addAttribute("packageForm", form);
        model.addAttribute("packages", adminPackageDaoImpl.getAllPackages());
        model.addAttribute("editMode", form.getPackageId() != null);
    }

    private void handlePackagePhoto(PackageForm form) throws Exception {
        if (form.getPhotoFile() == null || form.getPhotoFile().isEmpty()) {
            form.setPhotoPath(form.getCurrentPhotoPath());
            return;
        }

        String packageKey = (form.getPackageName() == null ? "package" : form.getPackageName())
                .replaceAll("[^a-zA-Z0-9._-]", "_");

        String photoPath = fileService.saveFile(form.getPhotoFile(), "package", packageKey);
        form.setPhotoPath(photoPath);
    }
}
