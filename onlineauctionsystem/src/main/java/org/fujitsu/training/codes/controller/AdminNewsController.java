package org.fujitsu.training.codes.controller;

import org.fujitsu.training.codes.helper.AdminNewsHelper;
import org.fujitsu.training.codes.model.form.NewsForm;
import org.fujitsu.training.codes.validator.NewsFormValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/news")
public class AdminNewsController {

	private static final String LOGIN_REDIRECT = "redirect:/app/login";
	private static final String VIEW_NAME = "adminNewsView";

	private final AdminNewsHelper adminNewsHelper;
	private final NewsFormValidator newsFormValidator;

	public AdminNewsController(AdminNewsHelper adminNewsHelper, NewsFormValidator newsFormValidator) {
		this.adminNewsHelper = adminNewsHelper;
		this.newsFormValidator = newsFormValidator;
	}

	@InitBinder("newsForm")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(newsFormValidator);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String loadNews(@RequestParam(value = "newsId", required = false) Integer newsId,
			@RequestParam(value = "success", required = false) String success, Model model, HttpSession session) {

		if (!isAdmin(session)) {
			return LOGIN_REDIRECT;
		}

		adminNewsHelper.prepareLoadNews(newsId, success, model);
		return VIEW_NAME;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveNews(@Validated @ModelAttribute("newsForm") NewsForm form, BindingResult result, Model model,
			HttpSession session) {

		if (!isAdmin(session)) {
			return LOGIN_REDIRECT;
		}

		return adminNewsHelper.processSaveNews(form, result, model);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteNews(@RequestParam("newsId") Integer newsId, Model model, HttpSession session) {

		if (!isAdmin(session)) {
			return LOGIN_REDIRECT;
		}

		return adminNewsHelper.processDeleteNews(newsId, model);
	}

	private boolean isAdmin(HttpSession session) {
		String username = (String) session.getAttribute("loggedInUsername");
		String userType = (String) session.getAttribute("loggedInUserType");
		return username != null && userType != null && "admin".equalsIgnoreCase(userType);
	}
}
