package org.fujitsu.training.codes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.LoginDaoImpl;
import org.fujitsu.training.codes.exceptions.AccountLockedException;
import org.fujitsu.training.codes.exceptions.InvalidCredentialsException;
import org.fujitsu.training.codes.model.data.User;
import org.fujitsu.training.codes.model.form.LoginForm;
import org.fujitsu.training.codes.validator.LoginFormValidator;
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
@RequestMapping("/login")
public class LoginController {
    private static final Logger logger = LogManager.getLogger(LoginController.class);

    private final LoginFormValidator loginFormValidator;
    private final LoginDaoImpl loginService;

    public LoginController(LoginFormValidator loginFormValidator, LoginDaoImpl loginService) {
        this.loginFormValidator = loginFormValidator;
        this.loginService = loginService;
    }

    @InitBinder("loginForm")
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(loginFormValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String loadForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "loginView";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submitForm(
            @Validated @ModelAttribute("loginForm") LoginForm form,
            BindingResult result,
            Model model,
            HttpSession session) {

        if (result.hasErrors()) {
            return "loginView";
        }

        try {
            User loggedInUser = loginService.login(form);
            session.setAttribute("loggedInUsername", loggedInUser.getUsername());
            session.setAttribute("loggedInUserType", loggedInUser.getUserType());
            model.addAttribute("loggedInUser", loggedInUser);
            return "loginSuccess";
        } catch (InvalidCredentialsException ex) {
            logger.warn("Invalid login attempt for username: {}", form.getUsername());
            result.reject("loginError", ex.getMessage());
        } catch (AccountLockedException ex) {
            logger.warn("Locked account login attempt for username: {}", form.getUsername());
            result.reject("loginError", ex.getMessage());
        } catch (Exception ex) {
            logger.error("Unexpected login error for username: {}", form.getUsername(), ex);
            result.reject("loginError", "Login failed due to an unexpected system error.");
        }

        return "loginView";
    }
}
