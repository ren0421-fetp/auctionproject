/*package org.fujitsu.training.codes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.AdminUserDaoImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {
    private static final Logger logger = LogManager.getLogger(AdminUserController.class);

    private final AdminUserDaoImpl adminUserDaoImpl;

    public AdminUserController(AdminUserDaoImpl adminUserDaoImpl) {
        this.adminUserDaoImpl = adminUserDaoImpl;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String loadUsers(
            @RequestParam(value = "userType", required = false) String userType,
            @RequestParam(value = "success", required = false) String success,
            Model model,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/app/login";
        }

        model.addAttribute("users", adminUserDaoImpl.getAllUsers(userType));
        model.addAttribute("selectedUserType", userType);

        if ("1".equals(success)) {
            model.addAttribute("userActionSuccess", "User status updated successfully.");
        }

        return "adminUserView";
    }

    @RequestMapping(value = "/lock", method = RequestMethod.POST)
    public String lockUser(@RequestParam("username") String username,
            Model model,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/app/login";
        }

        try {
            adminUserDaoImpl.lockUser(username);
            return "redirect:/app/admin/users?success=1";
        } catch (Exception ex) {
            logger.error("Failed to lock user {}: {}", username, ex.getMessage(), ex);
            model.addAttribute("users", adminUserDaoImpl.getAllUsers(null));
            model.addAttribute("userActionError", ex.getMessage());
            return "adminUserView";
        }
    }

    @RequestMapping(value = "/unlock", method = RequestMethod.POST)
    public String unlockUser(@RequestParam("username") String username,
            Model model,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/app/login";
        }

        try {
            adminUserDaoImpl.unlockUser(username);
            return "redirect:/app/admin/users?success=1";
        } catch (Exception ex) {
            logger.error("Failed to unlock user {}: {}", username, ex.getMessage(), ex);
            model.addAttribute("users", adminUserDaoImpl.getAllUsers(null));
            model.addAttribute("userActionError", ex.getMessage());
            return "adminUserView";
        }
    }

    private boolean isAdmin(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUsername");
        String userType = (String) session.getAttribute("loggedInUserType");
        return username != null && userType != null && "admin".equalsIgnoreCase(userType);
    }
}*/

package org.fujitsu.training.codes.controller;

import org.fujitsu.training.codes.helper.AdminUserHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    private static final String LOGIN_REDIRECT = "redirect:/app/login";
    private static final String VIEW_NAME = "adminUserView";

    private final AdminUserHelper adminUserHelper;

    public AdminUserController(AdminUserHelper adminUserHelper) {
        this.adminUserHelper = adminUserHelper;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String loadUsers(
            @RequestParam(value = "userType", required = false) String userType,
            @RequestParam(value = "success", required = false) String success,
            Model model,
            HttpSession session) {

        if (!isAdmin(session)) {
            return LOGIN_REDIRECT;
        }

        adminUserHelper.prepareLoadUsers(userType, success, model);
        return VIEW_NAME;
    }

    @RequestMapping(value = "/lock", method = RequestMethod.POST)
    public String lockUser(@RequestParam("username") String username,
            Model model,
            HttpSession session) {

        if (!isAdmin(session)) {
            return LOGIN_REDIRECT;
        }

        return adminUserHelper.processLockUser(username, model);
    }

    @RequestMapping(value = "/unlock", method = RequestMethod.POST)
    public String unlockUser(@RequestParam("username") String username,
            Model model,
            HttpSession session) {

        if (!isAdmin(session)) {
            return LOGIN_REDIRECT;
        }

        return adminUserHelper.processUnlockUser(username, model);
    }

    private boolean isAdmin(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUsername");
        String userType = (String) session.getAttribute("loggedInUserType");
        return username != null && userType != null && "admin".equalsIgnoreCase(userType);
    }
}

