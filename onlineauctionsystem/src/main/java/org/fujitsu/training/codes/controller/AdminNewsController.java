package org.fujitsu.training.codes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.AdminNewsDaoImpl;
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
    private static final Logger logger = LogManager.getLogger(AdminNewsController.class);

    private final AdminNewsDaoImpl adminNewsDaoImpl;
    private final NewsFormValidator newsFormValidator;

    public AdminNewsController(AdminNewsDaoImpl adminNewsDaoImpl,
            NewsFormValidator newsFormValidator) {
        this.adminNewsDaoImpl = adminNewsDaoImpl;
        this.newsFormValidator = newsFormValidator;
    }

    @InitBinder("newsForm")
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(newsFormValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String loadNews(
            @RequestParam(value = "newsId", required = false) Integer newsId,
            @RequestParam(value = "success", required = false) String success,
            Model model,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/app/login";
        }

        NewsForm form = newsId != null ? adminNewsDaoImpl.getNewsForm(newsId) : new NewsForm();
        model.addAttribute("newsForm", form == null ? new NewsForm() : form);
        model.addAttribute("newsList", adminNewsDaoImpl.getAllNews());
        model.addAttribute("editMode", newsId != null && form != null);

        if ("1".equals(success)) {
            model.addAttribute("newsSuccess", "News saved successfully.");
        }

        return "adminNewsView";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveNews(
            @Validated @ModelAttribute("newsForm") NewsForm form,
            BindingResult result,
            Model model,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/app/login";
        }

        if (result.hasErrors()) {
            model.addAttribute("newsList", adminNewsDaoImpl.getAllNews());
            model.addAttribute("editMode", form.getNewsId() != null);
            return "adminNewsView";
        }

        try {
            adminNewsDaoImpl.saveNews(form);
            return "redirect:/app/admin/news?success=1";
        } catch (Exception ex) {
            logger.error("Failed to save news: {}", ex.getMessage(), ex);
            model.addAttribute("newsList", adminNewsDaoImpl.getAllNews());
            model.addAttribute("editMode", form.getNewsId() != null);
            model.addAttribute("newsError", ex.getMessage());
            return "adminNewsView";
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteNews(@RequestParam("newsId") Integer newsId,
            Model model,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/app/login";
        }

        try {
            adminNewsDaoImpl.deleteNews(newsId);
            return "redirect:/app/admin/news?success=1";
        } catch (Exception ex) {
            model.addAttribute("newsForm", new NewsForm());
            model.addAttribute("newsList", adminNewsDaoImpl.getAllNews());
            model.addAttribute("newsError", ex.getMessage());
            return "adminNewsView";
        }
    }

    private boolean isAdmin(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUsername");
        String userType = (String) session.getAttribute("loggedInUserType");
        return username != null && userType != null && "admin".equalsIgnoreCase(userType);
    }
}
