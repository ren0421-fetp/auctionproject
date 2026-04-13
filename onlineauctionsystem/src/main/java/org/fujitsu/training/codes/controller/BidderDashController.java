package org.fujitsu.training.codes.controller;

import org.fujitsu.training.codes.helper.SessionRoleHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/bidder")
public class BidderDashController {

    private static final String LOGIN_REDIRECT = "redirect:/app/login";

    private final SessionRoleHelper sessionRoleHelper;

    public BidderDashController(SessionRoleHelper sessionRoleHelper) {
        this.sessionRoleHelper = sessionRoleHelper;
    }

    @RequestMapping("/home")
    public String showBidderHome(HttpSession session) {
        String username = sessionRoleHelper.getBidderUsername(session);
        if (username == null) {
            return LOGIN_REDIRECT;
        }

        return "bidderMainDash";
    }
}
