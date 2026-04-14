package org.fujitsu.training.codes.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.AdminUserDaoImpl;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class AdminUserHelper {
	private static final Logger logger = LogManager.getLogger("admin-flow");

	private static final String VIEW_NAME = "adminUserView";
	private static final String SUCCESS_REDIRECT = "redirect:/app/admin/users?success=1";

	private final AdminUserDaoImpl adminUserDaoImpl;

	public AdminUserHelper(AdminUserDaoImpl adminUserDaoImpl) {
		this.adminUserDaoImpl = adminUserDaoImpl;
	}

	public void prepareLoadUsers(String userType, String success, Model model) {
		logger.info("Loading admin user page. userType={}", userType);
		model.addAttribute("users", adminUserDaoImpl.getAllUsers(userType));
		model.addAttribute("selectedUserType", userType);

		if ("1".equals(success)) {
			model.addAttribute("userActionSuccess", "User status updated successfully.");
		}

		logger.info("Admin user page loaded. userType={}", userType);
	}

	public String processLockUser(String username, Model model) {
		logger.info("Processing user lock. username={}", username);
		try {
			adminUserDaoImpl.lockUser(username);
			logger.info("User lock completed. username={}", username);
			return SUCCESS_REDIRECT;
		} catch (Exception ex) {
			logger.error("Failed to lock user {}: {}", username, ex.getMessage(), ex);
			model.addAttribute("users", adminUserDaoImpl.getAllUsers(null));
			model.addAttribute("userActionError", ex.getMessage());
			return VIEW_NAME;
		}
	}

	public String processUnlockUser(String username, Model model) {
		logger.info("Processing user unlock. username={}", username);
		try {
			adminUserDaoImpl.unlockUser(username);
			logger.info("User unlock completed. username={}", username);
			return SUCCESS_REDIRECT;
		} catch (Exception ex) {
			logger.error("Failed to unlock user {}: {}", username, ex.getMessage(), ex);
			model.addAttribute("users", adminUserDaoImpl.getAllUsers(null));
			model.addAttribute("userActionError", ex.getMessage());
			return VIEW_NAME;
		}
	}
}
