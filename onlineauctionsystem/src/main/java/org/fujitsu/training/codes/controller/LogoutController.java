package org.fujitsu.training.codes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/logout")
public class LogoutController {

	@RequestMapping(method = RequestMethod.GET)
	public String logout(HttpSession session) {
		if (session != null) {
			session.invalidate();
		}
		return "redirect:/app/login";
	}
}
