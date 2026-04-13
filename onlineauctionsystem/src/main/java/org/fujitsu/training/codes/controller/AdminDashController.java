/*package org.fujitsu.training.codes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminDashController {

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String loadAdminDashboard(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUsername");
        String userType = (String) session.getAttribute("loggedInUserType");

        if (username == null || userType == null || !"admin".equalsIgnoreCase(userType)) {
            return "redirect:/app/login";
        }

        return "adminMainDash";
    }
}*/

package org.fujitsu.training.codes.controller;

import org.fujitsu.training.codes.helper.SessionRoleHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminDashController {

    private static final String LOGIN_REDIRECT = "redirect:/app/login";

    private final SessionRoleHelper sessionRoleHelper;

    public AdminDashController(SessionRoleHelper sessionRoleHelper) {
        this.sessionRoleHelper = sessionRoleHelper;
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String loadAdminDashboard(HttpSession session) {
        if (!sessionRoleHelper.isAdmin(session)) {
            return LOGIN_REDIRECT;
        }

        return "adminMainDash";
    }
}

