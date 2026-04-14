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
	public String loadBidConfirmations(@RequestParam(value = "success", required = false) String success, Model model,
			HttpSession session) {

		String adminUsername = getAdminUsername(session);
		if (adminUsername == null) {
			return LOGIN_REDIRECT;
		}

		adminBidHelper.prepareBidConfirmations(success, model);
		return VIEW_NAME;
	}

	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	public String confirmBid(@RequestParam("bidId") Integer bidId, Model model, HttpSession session) {

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
