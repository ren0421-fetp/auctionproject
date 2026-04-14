package org.fujitsu.training.codes.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.AdminUserPackageDaoImpl;
import org.fujitsu.training.codes.model.form.PackagePurchaseRequestReviewForm;
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
		model.addAttribute("reviewForm", new PackagePurchaseRequestReviewForm());
		model.addAttribute("bidders", adminUserPackageDaoImpl.getBidders());
		model.addAttribute("packages", adminUserPackageDaoImpl.getPackages());
		model.addAttribute("userPackageInfos", adminUserPackageDaoImpl.getAllUserPackageInfos());
		model.addAttribute("packagePurchaseRequests", adminUserPackageDaoImpl.getAllPackagePurchaseRequestInfos());

		if ("1".equals(success)) {
			model.addAttribute("assignSuccess", "Action completed successfully.");
		}

		logger.info("Admin user-package page loaded. userPackageId={}", userPackageId);
	}

	public String processAssignPackage(UserPackageForm form, BindingResult result, Model model) {
		logger.info("Processing manual user-package assignment. username={}, packageId={}", form.getUsername(),
				form.getPackageId());

		if (result.hasErrors()) {
			populatePage(model, form);
			logger.warn("Manual user-package validation failed. username={}, packageId={}", form.getUsername(),
					form.getPackageId());
			return VIEW_NAME;
		}

		try {
			adminUserPackageDaoImpl.assignPackage(form);
			logger.info("Manual user-package assignment completed. username={}, packageId={}", form.getUsername(),
					form.getPackageId());
			return SUCCESS_REDIRECT;
		} catch (Exception ex) {
			logger.error("Manual user-package assignment failed. username={}, packageId={}: {}", form.getUsername(),
					form.getPackageId(), ex.getMessage(), ex);
			populatePage(model, form);
			model.addAttribute("assignError", ex.getMessage());
			return VIEW_NAME;
		}
	}

	public String processApproveRequest(PackagePurchaseRequestReviewForm form, String adminUsername, Model model) {
		logger.info("Processing package request approval. requestId={}, adminUsername={}", form.getRequestId(),
				adminUsername);
		try {
			adminUserPackageDaoImpl.approvePackageRequest(form, adminUsername);
			logger.info("Package request approval completed. requestId={}, adminUsername={}", form.getRequestId(),
					adminUsername);
			return SUCCESS_REDIRECT;
		} catch (Exception ex) {
			logger.error("Package request approval failed. requestId={}, adminUsername={}: {}", form.getRequestId(),
					adminUsername, ex.getMessage(), ex);
			populatePage(model, new UserPackageForm());
			model.addAttribute("assignError", ex.getMessage());
			return VIEW_NAME;
		}
	}

	public String processRejectRequest(PackagePurchaseRequestReviewForm form, String adminUsername, Model model) {
		logger.info("Processing package request rejection. requestId={}, adminUsername={}", form.getRequestId(),
				adminUsername);
		try {
			adminUserPackageDaoImpl.rejectPackageRequest(form, adminUsername);
			logger.info("Package request rejection completed. requestId={}, adminUsername={}", form.getRequestId(),
					adminUsername);
			return SUCCESS_REDIRECT;
		} catch (Exception ex) {
			logger.error("Package request rejection failed. requestId={}, adminUsername={}: {}", form.getRequestId(),
					adminUsername, ex.getMessage(), ex);
			populatePage(model, new UserPackageForm());
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
		model.addAttribute("reviewForm", new PackagePurchaseRequestReviewForm());
		model.addAttribute("bidders", adminUserPackageDaoImpl.getBidders());
		model.addAttribute("packages", adminUserPackageDaoImpl.getPackages());
		model.addAttribute("userPackageInfos", adminUserPackageDaoImpl.getAllUserPackageInfos());
		model.addAttribute("packagePurchaseRequests", adminUserPackageDaoImpl.getAllPackagePurchaseRequestInfos());
	}
}
