/*package org.fujitsu.training.codes.controller;

import org.fujitsu.training.codes.dao.impl.FeedbackFlowDaoImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/news")
public class NewsController {

    private final FeedbackFlowDaoImpl feedbackFlowDaoImpl;

    public NewsController(FeedbackFlowDaoImpl feedbackFlowDaoImpl) {
        this.feedbackFlowDaoImpl = feedbackFlowDaoImpl;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String loadNews(Model model, HttpSession session) {
        String username = (String) session.getAttribute("loggedInUsername");
        String userType = (String) session.getAttribute("loggedInUserType");

        if (username == null || userType == null) {
            return "redirect:/app/login";
        }

        model.addAttribute("newsList", feedbackFlowDaoImpl.getAllNews());
        return "newsView";
    }
}*/

package org.fujitsu.training.codes.controller;

import org.fujitsu.training.codes.helper.NewsHelper;
import org.fujitsu.training.codes.helper.SessionRoleHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/news")
public class NewsController {

    private static final String LOGIN_REDIRECT = "redirect:/app/login";

    private final NewsHelper newsHelper;
    private final SessionRoleHelper sessionRoleHelper;

    public NewsController(NewsHelper newsHelper,
            SessionRoleHelper sessionRoleHelper) {
        this.newsHelper = newsHelper;
        this.sessionRoleHelper = sessionRoleHelper;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String loadNews(Model model, HttpSession session) {
        if (!sessionRoleHelper.isLoggedIn(session)) {
            return LOGIN_REDIRECT;
        }

        newsHelper.prepareLoadNews(model);
        return "newsView";
    }
}

