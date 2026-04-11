package org.fujitsu.training.codes.dao.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private final SqlSessionFactory ssf;

    public LoginDaoImpl(SqlSessionFactory ssf) {
        this.ssf = ssf;
    }

    public User login(LoginForm form) throws Exception {
        String username = form.getUsername() == null ? "" : form.getUsername().trim();
        logger.info("Manual session login attempt for username: {}", username);

        SqlSession sess = ssf.openSession();
        try {
            // 1. Fetch User using the same mapper ID as registration
            User user = sess.selectOne("org.fujitsu.training.codes.dao.UserDao.selectByUsername", username);
            
            if (user == null) {
                logger.warn("Login failed. Username not found: {}", username);
                throw new InvalidCredentialsException("Invalid username or password.");
            }

            // 2. Check Lockout Status
            if (Boolean.TRUE.equals(user.getIsLocked())) {
                logger.warn("Login blocked. Account is locked: {}", username);
                throw new AccountLockedException("Your account is locked. Please contact the administrator.");
            }

            // 3. Verify Password
            String incomingHash = hashPassword(form.getPassword());
            if (!incomingHash.equals(user.getPasswordHash())) {
                int nextAttempts = (user.getFailedLoginAttempts() == null ? 0 : user.getFailedLoginAttempts()) + 1;

                if (nextAttempts >= 3) {
                    sess.update("org.fujitsu.training.codes.dao.UserDao.lockUser", username);
                    sess.commit();
                    logger.warn("Account locked after 3 failed attempts: {}", username);
                    throw new AccountLockedException("Your account has been locked after 3 failed attempts.");
                }

                sess.update("org.fujitsu.training.codes.dao.UserDao.updateFailedLoginAttempts", 
                            new java.util.HashMap<String, Object>() {{
                                put("username", username);
                                put("failedLoginAttempts", nextAttempts);
                            }});
                sess.commit();
                logger.warn("Invalid password for: {}. Attempts: {}", username, nextAttempts);
                throw new InvalidCredentialsException("Invalid username or password.");
            }

            // 4. Success - Reset Attempts
            if (user.getFailedLoginAttempts() != null && user.getFailedLoginAttempts() > 0) {
                sess.update("org.fujitsu.training.codes.dao.UserDao.resetFailedLoginAttempts", username);
                sess.commit();
            }

            logger.info("Login successful for username: {}", username);
            return user;

        } catch (InvalidCredentialsException | AccountLockedException e) {
            sess.rollback();
            throw e;
        } catch (Exception e) {
            logger.error("Database error during login for {}: {}", username, e.getMessage());
            sess.rollback();
            throw e;
        } finally {
            sess.close();
        }
    }

    private String hashPassword(String rawPassword) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
        return HexFormat.of().formatHex(hash);
    }
}