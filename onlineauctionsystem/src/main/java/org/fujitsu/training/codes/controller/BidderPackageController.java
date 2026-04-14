package org.fujitsu.training.codes.controller;

import org.fujitsu.training.codes.helper.BidderPackageHelper;
import org.fujitsu.training.codes.helper.SessionRoleHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/bidder/package")
public class BidderPackageController {

	private static final String LOGIN_REDIRECT = "redirect:/app/login";
	private static final String VIEW_NAME = "bidderPackageListView";

	private final BidderPackageHelper bidderPackageHelper;
	private final SessionRoleHelper sessionRoleHelper;

	public BidderPackageController(BidderPackageHelper bidderPackageHelper, SessionRoleHelper sessionRoleHelper) {
		this.bidderPackageHelper = bidderPackageHelper;
		this.sessionRoleHelper = sessionRoleHelper;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String loadPackages(@RequestParam(value = "success", required = false) String success, Model model,
			HttpSession session) {

		String bidderUsername = sessionRoleHelper.getBidderUsername(session);
		if (bidderUsername == null) {
			return LOGIN_REDIRECT;
		}

		bidderPackageHelper.prepareLoadPackages(success, bidderUsername, model);
		return VIEW_NAME;
	}

	@RequestMapping(value = "/purchase", method = RequestMethod.POST)
	public String purchasePackage(@RequestParam("packageId") Integer packageId, Model model, HttpSession session) {

		String bidderUsername = sessionRoleHelper.getBidderUsername(session);
		if (bidderUsername == null) {
			return LOGIN_REDIRECT;
		}

		return bidderPackageHelper.processPurchasePackage(packageId, bidderUsername, model);
	}
}
