package org.fujitsu.training.codes.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.FeedbackFlowDaoImpl;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class AdminFeedbackHelper {
	private static final Logger logger = LogManager.getLogger("admin-flow");

	private final FeedbackFlowDaoImpl feedbackFlowDaoImpl;

	public AdminFeedbackHelper(FeedbackFlowDaoImpl feedbackFlowDaoImpl) {
		this.feedbackFlowDaoImpl = feedbackFlowDaoImpl;
	}

	public void prepareLoadFeedback(Model model) {
		logger.info("Loading admin feedback page.");
		model.addAttribute("feedbackList", feedbackFlowDaoImpl.getAllFeedback());
		logger.info("Admin feedback page loaded.");
	}
}
