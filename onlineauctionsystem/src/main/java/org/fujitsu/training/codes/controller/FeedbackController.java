/*package org.fujitsu.training.codes.controller;

import org.fujitsu.training.codes.dao.impl.FeedbackFlowDaoImpl;
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
    private final FeedbackFlowDaoImpl feedbackFlowDaoImpl;
    private final FeedbackFormValidator feedbackFormValidator;

    public FeedbackController(FeedbackFlowDaoImpl feedbackFlowDaoImpl,
            FeedbackFormValidator feedbackFormValidator) {
        this.feedbackFlowDaoImpl = feedbackFlowDaoImpl;
        this.feedbackFormValidator = feedbackFormValidator;
    }

    @InitBinder("feedbackForm")
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(feedbackFormValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String loadForm(Model model, HttpSession session) {
        FeedbackForm form = new FeedbackForm();

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            form.setFirstName(loggedInUser.getFirstName());
            form.setEmail(loggedInUser.getEmail());
            form.setContact(loggedInUser.getContactNo());
        }

        model.addAttribute("feedbackForm", form);
        model.addAttribute("newsList", feedbackFlowDaoImpl.getAllNews());
        return "feedbackView";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submitForm(
            @Validated @ModelAttribute("feedbackForm") FeedbackForm form,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("newsList", feedbackFlowDaoImpl.getAllNews());
            return "feedbackView";
        }

        try {
            feedbackFlowDaoImpl.saveFeedback(form);
            return "redirect:/app/feedback?success=1";
        } catch (Exception ex) {
            model.addAttribute("newsList", feedbackFlowDaoImpl.getAllNews());
            model.addAttribute("feedbackError", ex.getMessage());
            return "feedbackView";
        }
    }
}
*/

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

    public FeedbackController(FeedbackHelper feedbackHelper,
            FeedbackFormValidator feedbackFormValidator) {
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
    public String submitForm(
            @Validated @ModelAttribute("feedbackForm") FeedbackForm form,
            BindingResult result,
            Model model) {

        return feedbackHelper.processSubmitForm(form, result, model);
    }
}
