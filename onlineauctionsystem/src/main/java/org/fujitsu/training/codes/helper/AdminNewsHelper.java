/*package org.fujitsu.training.codes.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.AdminNewsDaoImpl;
import org.fujitsu.training.codes.model.form.NewsForm;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Component
public class AdminNewsHelper {
    private static final Logger logger = LogManager.getLogger(AdminNewsHelper.class);

    private static final String VIEW_NAME = "adminNewsView";
    private static final String SUCCESS_REDIRECT = "redirect:/app/admin/news?success=1";

    private final AdminNewsDaoImpl adminNewsDaoImpl;

    public AdminNewsHelper(AdminNewsDaoImpl adminNewsDaoImpl) {
        this.adminNewsDaoImpl = adminNewsDaoImpl;
    }

    public void prepareLoadNews(Integer newsId, String success, Model model) {
        NewsForm form = resolveNewsForm(newsId);

        model.addAttribute("newsForm", form);
        model.addAttribute("newsList", adminNewsDaoImpl.getAllNews());
        model.addAttribute("editMode", form.getNewsId() != null);

        if ("1".equals(success)) {
            model.addAttribute("newsSuccess", "News saved successfully.");
        }
    }

    public String processSaveNews(NewsForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            populatePage(model, form);
            return VIEW_NAME;
        }

        try {
            adminNewsDaoImpl.saveNews(form);
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Failed to save news: {}", ex.getMessage(), ex);
            populatePage(model, form);
            model.addAttribute("newsError", ex.getMessage());
            return VIEW_NAME;
        }
    }

    public String processDeleteNews(Integer newsId, Model model) {
        try {
            adminNewsDaoImpl.deleteNews(newsId);
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            model.addAttribute("newsForm", new NewsForm());
            model.addAttribute("newsList", adminNewsDaoImpl.getAllNews());
            model.addAttribute("editMode", false);
            model.addAttribute("newsError", ex.getMessage());
            return VIEW_NAME;
        }
    }

    private NewsForm resolveNewsForm(Integer newsId) {
        if (newsId == null) {
            return new NewsForm();
        }

        NewsForm form = adminNewsDaoImpl.getNewsForm(newsId);
        return form == null ? new NewsForm() : form;
    }

    private void populatePage(Model model, NewsForm form) {
        model.addAttribute("newsForm", form);
        model.addAttribute("newsList", adminNewsDaoImpl.getAllNews());
        model.addAttribute("editMode", form.getNewsId() != null);
    }
}
*/

package org.fujitsu.training.codes.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.AdminNewsDaoImpl;
import org.fujitsu.training.codes.model.form.NewsForm;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Component
public class AdminNewsHelper {
    private static final Logger logger = LogManager.getLogger("admin-flow");

    private static final String VIEW_NAME = "adminNewsView";
    private static final String SUCCESS_REDIRECT = "redirect:/app/admin/news?success=1";

    private final AdminNewsDaoImpl adminNewsDaoImpl;

    public AdminNewsHelper(AdminNewsDaoImpl adminNewsDaoImpl) {
        this.adminNewsDaoImpl = adminNewsDaoImpl;
    }

    public void prepareLoadNews(Integer newsId, String success, Model model) {
        logger.info("Loading admin news page. newsId={}", newsId);

        NewsForm form = resolveNewsForm(newsId);
        model.addAttribute("newsForm", form);
        model.addAttribute("newsList", adminNewsDaoImpl.getAllNews());
        model.addAttribute("editMode", form.getNewsId() != null);

        if ("1".equals(success)) {
            model.addAttribute("newsSuccess", "News saved successfully.");
        }

        logger.info("Admin news page loaded. newsId={}", newsId);
    }

    public String processSaveNews(NewsForm form, BindingResult result, Model model) {
        logger.info("Processing news save. newsId={}, title={}", form.getNewsId(), form.getNewsTitle());

        if (result.hasErrors()) {
            populatePage(model, form);
            logger.warn("News validation failed. newsId={}, title={}", form.getNewsId(), form.getNewsTitle());
            return VIEW_NAME;
        }

        try {
            adminNewsDaoImpl.saveNews(form);
            logger.info("News save completed. newsId={}, title={}", form.getNewsId(), form.getNewsTitle());
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Failed to save news: {}", ex.getMessage(), ex);
            populatePage(model, form);
            model.addAttribute("newsError", ex.getMessage());
            return VIEW_NAME;
        }
    }

    public String processDeleteNews(Integer newsId, Model model) {
        logger.info("Processing news delete. newsId={}", newsId);
        try {
            adminNewsDaoImpl.deleteNews(newsId);
            logger.info("News delete completed. newsId={}", newsId);
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Failed to delete news {}: {}", newsId, ex.getMessage(), ex);
            model.addAttribute("newsForm", new NewsForm());
            model.addAttribute("newsList", adminNewsDaoImpl.getAllNews());
            model.addAttribute("editMode", false);
            model.addAttribute("newsError", ex.getMessage());
            return VIEW_NAME;
        }
    }

    private NewsForm resolveNewsForm(Integer newsId) {
        if (newsId == null) {
            return new NewsForm();
        }

        NewsForm form = adminNewsDaoImpl.getNewsForm(newsId);
        return form == null ? new NewsForm() : form;
    }

    private void populatePage(Model model, NewsForm form) {
        model.addAttribute("newsForm", form);
        model.addAttribute("newsList", adminNewsDaoImpl.getAllNews());
        model.addAttribute("editMode", form.getNewsId() != null);
    }
}
