package org.fujitsu.training.codes.controller;

import org.fujitsu.training.codes.helper.FeedbackHelper;
import org.fujitsu.training.codes.model.data.User;
import org.fujitsu.training.codes.model.form.FeedbackForm;
import org.fujitsu.training.codes.validator.FeedbackFormValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

	private final FeedbackHelper feedbackHelper;
	private final FeedbackFormValidator feedbackFormValidator;

	public FeedbackController(FeedbackHelper feedbackHelper, FeedbackFormValidator feedbackFormValidator) {
		this.feedbackHelper = feedbackHelper;
		this.feedbackFormValidator = feedbackFormValidator;
	}

	@InitBinder("feedbackForm")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(feedbackFormValidator);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String loadForm(Model model, HttpSession session) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		feedbackHelper.prepareLoadForm(model, loggedInUser);
		return "feedbackView";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submitForm(@Validated @ModelAttribute("feedbackForm") FeedbackForm form, BindingResult result,
			Model model) {

		return feedbackHelper.processSubmitForm(form, result, model);
	}
}
