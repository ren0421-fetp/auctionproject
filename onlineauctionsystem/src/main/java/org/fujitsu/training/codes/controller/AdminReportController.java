package org.fujitsu.training.codes.controller;

import org.fujitsu.training.codes.helper.AdminReportHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/reports")
public class AdminReportController {

	private static final String LOGIN_REDIRECT = "redirect:/app/login";

	private final AdminReportHelper adminReportHelper;

	public AdminReportController(AdminReportHelper adminReportHelper) {
		this.adminReportHelper = adminReportHelper;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String loadReportPage(Model model, HttpSession session) {
		if (!isAdmin(session)) {
			return LOGIN_REDIRECT;
		}

		adminReportHelper.prepareLoadReportPage(model);
		return "adminReportView";
	}

	@RequestMapping(value = "/pdf", method = RequestMethod.GET)
	public ResponseEntity<byte[]> downloadPdf(@RequestParam("type") String type, HttpSession session) throws Exception {

		if (!isAdmin(session)) {
			return ResponseEntity.status(302).header("Location", "/app/login").build();
		}

		return adminReportHelper.buildPdfResponse(type);
	}

	private boolean isAdmin(HttpSession session) {
		String username = (String) session.getAttribute("loggedInUsername");
		String userType = (String) session.getAttribute("loggedInUserType");
		return username != null && userType != null && "admin".equalsIgnoreCase(userType);
	}
}
