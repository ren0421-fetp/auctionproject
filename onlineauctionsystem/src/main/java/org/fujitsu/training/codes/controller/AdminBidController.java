/*package org.fujitsu.training.codes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.AdminBidDaoImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/bids")
public class AdminBidController {
    private static final Logger logger = LogManager.getLogger(AdminBidController.class);

    private final AdminBidDaoImpl adminBidDaoImpl;

    public AdminBidController(AdminBidDaoImpl adminBidDaoImpl) {
        this.adminBidDaoImpl = adminBidDaoImpl;
    }

    @RequestMapping(value = "/confirmations", method = RequestMethod.GET)
    public String loadBidConfirmations(
            @RequestParam(value = "success", required = false) String success,
            Model model,
            HttpSession session) {

        String adminUsername = getAdminUsername(session);
        if (adminUsername == null) {
            return "redirect:/app/login";
        }

        model.addAttribute("allBids", adminBidDaoImpl.getAllBidsForAdmin());

        if ("1".equals(success)) {
        	model.addAttribute("confirmSuccess", "Winning bid confirmed and product closed successfully.");
        }

        return "adminBidConfirmationView";
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public String confirmBid(
            @RequestParam("bidId") Integer bidId,
            Model model,
            HttpSession session) {

        String adminUsername = getAdminUsername(session);
        if (adminUsername == null) {
            return "redirect:/app/login";
        }

        try {
            adminBidDaoImpl.confirmWinningBid(bidId);
            return "redirect:/app/admin/bids/confirmations?success=1";
        } catch (Exception ex) {
            logger.error("Admin {} failed to confirm bid {}: {}", adminUsername, bidId, ex.getMessage(), ex);
            model.addAttribute("allBids", adminBidDaoImpl.getAllBidsForAdmin());
            model.addAttribute("confirmError", ex.getMessage());
            return "adminBidConfirmationView";
        }
    }

    private String getAdminUsername(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUsername");
        String userType = (String) session.getAttribute("loggedInUserType");

        if (username == null || userType == null || !"admin".equalsIgnoreCase(userType)) {
            return null;
        }
        return username;
    }
}*/
package org.fujitsu.training.codes.controller;

import org.fujitsu.training.codes.helper.AdminBidHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/bids")
public class AdminBidController {

    private static final String LOGIN_REDIRECT = "redirect:/app/login";
    private static final String VIEW_NAME = "adminBidConfirmationView";

    private final AdminBidHelper adminBidHelper;

    public AdminBidController(AdminBidHelper adminBidHelper) {
        this.adminBidHelper = adminBidHelper;
    }

    @RequestMapping(value = "/confirmations", method = RequestMethod.GET)
    public String loadBidConfirmations(
            @RequestParam(value = "success", required = false) String success,
            Model model,
            HttpSession session) {

        String adminUsername = getAdminUsername(session);
        if (adminUsername == null) {
            return LOGIN_REDIRECT;
        }

        adminBidHelper.prepareBidConfirmations(success, model);
        return VIEW_NAME;
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public String confirmBid(
            @RequestParam("bidId") Integer bidId,
            Model model,
            HttpSession session) {

        String adminUsername = getAdminUsername(session);
        if (adminUsername == null) {
            return LOGIN_REDIRECT;
        }

        return adminBidHelper.processConfirmBid(bidId, adminUsername, model);
    }

    private String getAdminUsername(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUsername");
        String userType = (String) session.getAttribute("loggedInUserType");

        if (username == null || userType == null || !"admin".equalsIgnoreCase(userType)) {
            return null;
        }
        return username;
    }
}

