package org.fujitsu.training.codes.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.FeedbackFlowDaoImpl;
import org.fujitsu.training.codes.model.data.User;
import org.fujitsu.training.codes.model.form.FeedbackForm;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Component
public class FeedbackHelper {
	private static final Logger logger = LogManager.getLogger("common-flow");

	private static final String VIEW_NAME = "feedbackView";
	private static final String SUCCESS_REDIRECT = "redirect:/app/feedback?success=1";

	private final FeedbackFlowDaoImpl feedbackFlowDaoImpl;

	public FeedbackHelper(FeedbackFlowDaoImpl feedbackFlowDaoImpl) {
		this.feedbackFlowDaoImpl = feedbackFlowDaoImpl;
	}

	public void prepareLoadForm(Model model, User loggedInUser) {
		logger.info("Loading feedback page.");

		FeedbackForm form = new FeedbackForm();

		if (loggedInUser != null) {
			form.setFirstName(loggedInUser.getFirstName());
			form.setEmail(loggedInUser.getEmail());
			form.setContact(loggedInUser.getContactNo());
		}

		model.addAttribute("feedbackForm", form);
		model.addAttribute("newsList", feedbackFlowDaoImpl.getAllNews());

		logger.info("Feedback page loaded.");
	}

	public String processSubmitForm(FeedbackForm form, BindingResult result, Model model) {
		logger.info("Processing feedback submission.");

		if (result.hasErrors()) {
			model.addAttribute("newsList", feedbackFlowDaoImpl.getAllNews());
			logger.warn("Feedback validation failed.");
			return VIEW_NAME;
		}

		try {
			feedbackFlowDaoImpl.saveFeedback(form);
			logger.info("Feedback submission completed.");
			return SUCCESS_REDIRECT;
		} catch (Exception ex) {
			logger.error("Failed to save feedback: {}", ex.getMessage(), ex);
			model.addAttribute("newsList", feedbackFlowDaoImpl.getAllNews());
			model.addAttribute("feedbackError", ex.getMessage());
			return VIEW_NAME;
		}
	}
}
