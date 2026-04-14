package org.fujitsu.training.codes.controller;

import org.fujitsu.training.codes.helper.AdminFeedbackHelper;
import org.fujitsu.training.codes.helper.SessionRoleHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/feedback")
public class AdminFeedbackController {

	private static final String LOGIN_REDIRECT = "redirect:/app/login";

	private final AdminFeedbackHelper adminFeedbackHelper;
	private final SessionRoleHelper sessionRoleHelper;

	public AdminFeedbackController(AdminFeedbackHelper adminFeedbackHelper, SessionRoleHelper sessionRoleHelper) {
		this.adminFeedbackHelper = adminFeedbackHelper;
		this.sessionRoleHelper = sessionRoleHelper;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String loadFeedback(Model model, HttpSession session) {
		if (!sessionRoleHelper.isAdmin(session)) {
			return LOGIN_REDIRECT;
		}

		adminFeedbackHelper.prepareLoadFeedback(model);
		return "adminFeedbackView";
	}
}
