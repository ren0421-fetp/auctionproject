package org.fujitsu.training.codes.dao.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.UserDao;
import org.fujitsu.training.codes.exceptions.AccountLockedException;
import org.fujitsu.training.codes.exceptions.InvalidCredentialsException;
import org.fujitsu.training.codes.model.data.User;
import org.fujitsu.training.codes.model.form.LoginForm;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class LoginDaoImpl {
    private static final Logger logger = LogManager.getLogger(LoginDaoImpl.class);

    private final UserDao userDao;

    public LoginDaoImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public User login(LoginForm form) {
        String username = form.getUsername() == null ? "" : form.getUsername().trim();
        logger.info("Login attempt for username: {}", username);

        User user = userDao.selectByUsername(username);
        if (user == null) {
            logger.warn("Login failed. Username not found: {}", username);
            throw new InvalidCredentialsException("Invalid username or password.");
        }

        if (Boolean.TRUE.equals(user.getIsLocked())) {
            logger.warn("Login blocked. Account is locked: {}", username);
            throw new AccountLockedException("Your account is locked. Please contact the administrator.");
        }

        String incomingPasswordHash = hashPassword(form.getPassword());
        if (!incomingPasswordHash.equals(user.getPasswordHash())) {
            int currentAttempts = user.getFailedLoginAttempts() == null ? 0 : user.getFailedLoginAttempts();
            int nextAttempts = currentAttempts + 1;

            if (nextAttempts >= 3) {
                int updated = userDao.lockUser(username);
                if (updated != 1) {
                    logger.error("Failed to lock account for username: {}", username);
                    throw new IllegalStateException("Unable to update login state.");
                }

                logger.warn("Account locked after 3 failed attempts: {}", username);
                throw new AccountLockedException("Your account has been locked after 3 failed attempts.");
            }

            int updated = userDao.updateFailedLoginAttempts(username, nextAttempts);
            if (updated != 1) {
                logger.error("Failed to update failed login attempts for username: {}", username);
                throw new IllegalStateException("Unable to update login state.");
            }

            logger.warn("Invalid password for username: {}. Failed attempts: {}", username, nextAttempts);
            throw new InvalidCredentialsException("Invalid username or password.");
        }

        if (user.getFailedLoginAttempts() != null && user.getFailedLoginAttempts() > 0) {
            int updated = userDao.resetFailedLoginAttempts(username);
            if (updated != 1) {
                logger.error("Failed to reset failed login attempts for username: {}", username);
                throw new IllegalStateException("Unable to reset login state.");
            }
        }

        user.setFailedLoginAttempts(0);
        logger.info("Login successful for username: {}", username);
        return user;
    }

    private String hashPassword(String rawPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (Exception ex) {
            logger.error("Password hashing failed during login.", ex);
            throw new IllegalStateException("Unable to process login request.", ex);
        }
    }
}
