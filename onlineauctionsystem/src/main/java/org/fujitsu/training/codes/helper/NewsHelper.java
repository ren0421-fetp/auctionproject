package org.fujitsu.training.codes.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.FeedbackFlowDaoImpl;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class NewsHelper {
	private static final Logger logger = LogManager.getLogger("common-flow");

	private final FeedbackFlowDaoImpl feedbackFlowDaoImpl;

	public NewsHelper(FeedbackFlowDaoImpl feedbackFlowDaoImpl) {
		this.feedbackFlowDaoImpl = feedbackFlowDaoImpl;
	}

	public void prepareLoadNews(Model model) {
		logger.info("Loading news page.");
		model.addAttribute("newsList", feedbackFlowDaoImpl.getAllNews());
		logger.info("News page loaded.");
	}
}
