/*package org.fujitsu.training.codes.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.LoginDaoImpl;
import org.fujitsu.training.codes.exceptions.AccountLockedException;
import org.fujitsu.training.codes.exceptions.InvalidCredentialsException;
import org.fujitsu.training.codes.model.data.User;
import org.fujitsu.training.codes.model.form.LoginForm;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import jakarta.servlet.http.HttpSession;

@Component
public class LoginHelper {
    private static final Logger logger = LogManager.getLogger(LoginHelper.class);

    private final LoginDaoImpl loginDaoImpl;

    public LoginHelper(LoginDaoImpl loginDaoImpl) {
        this.loginDaoImpl = loginDaoImpl;
    }

    public void prepareLoadForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
    }

    public String processSubmitForm(LoginForm form, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            logger.warn("Login form validation failed for user: {}", form.getUsername());
            return "loginView";
        }

        try {
            User loggedInUser = loginDaoImpl.login(form);

            session.setAttribute("loggedInUser", loggedInUser);
            session.setAttribute("loggedInUsername", loggedInUser.getUsername());
            session.setAttribute("loggedInUserType", loggedInUser.getUserType());

            logger.info("User {} logged in successfully. Redirecting...", loggedInUser.getUsername());

            String role = loggedInUser.getUserType() == null
                    ? ""
                    : loggedInUser.getUserType().toLowerCase();

            if ("admin".equals(role)) {
                return "redirect:/app/admin/home";
            } else if ("seller".equals(role)) {
                return "sellerMainDash";
            } else {
                return "bidderMainDash";
            }
        } catch (InvalidCredentialsException ex) {
            result.reject("loginError", ex.getMessage());
        } catch (AccountLockedException ex) {
            result.reject("loginError", ex.getMessage());
        } catch (Exception ex) {
            logger.error("Unexpected error during login: {}", ex.getMessage(), ex);
            result.reject("loginError", "Login failed due to an unexpected system error.");
        }

        return "loginView";
    }
}*/

package org.fujitsu.training.codes.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.LoginDaoImpl;
import org.fujitsu.training.codes.exceptions.AccountLockedException;
import org.fujitsu.training.codes.exceptions.InvalidCredentialsException;
import org.fujitsu.training.codes.model.data.User;
import org.fujitsu.training.codes.model.form.LoginForm;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import jakarta.servlet.http.HttpSession;

@Component
public class LoginHelper {
    private static final Logger logger = LogManager.getLogger("auth-flow");

    private final LoginDaoImpl loginDaoImpl;

    public LoginHelper(LoginDaoImpl loginDaoImpl) {
        this.loginDaoImpl = loginDaoImpl;
    }

    public void prepareLoadForm(Model model) {
        logger.info("Loading login page.");
        model.addAttribute("loginForm", new LoginForm());
        logger.info("Login page loaded.");
    }

    public String processSubmitForm(LoginForm form, BindingResult result, HttpSession session) {
        logger.info("Processing login for username={}.", form.getUsername());

        if (result.hasErrors()) {
            logger.warn("Login validation failed for username={}.", form.getUsername());
            return "loginView";
        }

        try {
            User loggedInUser = loginDaoImpl.login(form);

            session.setAttribute("loggedInUser", loggedInUser);
            session.setAttribute("loggedInUsername", loggedInUser.getUsername());
            session.setAttribute("loggedInUserType", loggedInUser.getUserType());

            logger.info("Login completed successfully for username={}.", loggedInUser.getUsername());

            String role = loggedInUser.getUserType() == null ? "" : loggedInUser.getUserType().toLowerCase();
            if ("admin".equals(role)) {
                return "redirect:/app/admin/home";
            } else if ("seller".equals(role)) {
                return "sellerMainDash";
            } else {
                return "bidderMainDash";
            }
        } catch (InvalidCredentialsException ex) {
            logger.error("Login failed for username={}: {}", form.getUsername(), ex.getMessage(), ex);
            result.reject("loginError", ex.getMessage());
        } catch (AccountLockedException ex) {
            logger.error("Login failed for username={}: {}", form.getUsername(), ex.getMessage(), ex);
            result.reject("loginError", ex.getMessage());
        } catch (Exception ex) {
            logger.error("Login failed for username={}: {}", form.getUsername(), ex.getMessage(), ex);
            result.reject("loginError", "Login failed due to an unexpected system error.");
        }

        return "loginView";
    }
}

