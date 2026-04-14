package org.fujitsu.training.codes.controller;

import org.fujitsu.training.codes.helper.SellerDashHelper;
import org.fujitsu.training.codes.helper.SessionRoleHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/seller")
public class SellerDashController {

	private static final String LOGIN_REDIRECT = "redirect:/app/login";

	private final SellerDashHelper sellerDashHelper;
	private final SessionRoleHelper sessionRoleHelper;

	public SellerDashController(SellerDashHelper sellerDashHelper, SessionRoleHelper sessionRoleHelper) {
		this.sellerDashHelper = sellerDashHelper;
		this.sessionRoleHelper = sessionRoleHelper;
	}

	@RequestMapping("/home")
	public String showSellerHome(HttpSession session, Model model) {
		String username = sessionRoleHelper.getSellerUsername(session);
		if (username == null) {
			return LOGIN_REDIRECT;
		}

		return sellerDashHelper.prepareSellerHome(username, model);
	}
}
