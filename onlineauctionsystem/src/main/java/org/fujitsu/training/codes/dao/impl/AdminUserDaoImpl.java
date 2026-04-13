/*package org.fujitsu.training.codes.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.model.data.User;
import org.springframework.stereotype.Repository;

@Repository
public class AdminUserDaoImpl {
    private static final Logger logger = LogManager.getLogger(AdminUserDaoImpl.class);
    private final SqlSessionFactory ssf;

    public AdminUserDaoImpl(SqlSessionFactory ssf) {
        this.ssf = ssf;
    }

    public List<User> getAllUsers(String userType) {
        try (SqlSession sess = ssf.openSession()) {
            return sess.selectList("org.fujitsu.training.codes.dao.UserDao.selectAllUsers", userType);
        }
    }

    public void lockUser(String username) throws Exception {
        logger.info("Locking user {}", username);

        SqlSession sess = ssf.openSession();
        try {
            Integer updated = sess.update("org.fujitsu.training.codes.dao.UserDao.lockUser", username);
            if (updated == null || updated != 1) {
                throw new IllegalStateException("Failed to lock user.");
            }
            sess.commit();
        } catch (Exception ex) {
            sess.rollback();
            logger.error("Failed to lock user {}: {}", username, ex.getMessage(), ex);
            throw ex;
        } finally {
            sess.close();
        }
    }

    public void unlockUser(String username) throws Exception {
        logger.info("Unlocking user {}", username);

        SqlSession sess = ssf.openSession();
        try {
            Integer updated = sess.update("org.fujitsu.training.codes.dao.UserDao.unlockUser", username);
            if (updated == null || updated != 1) {
                throw new IllegalStateException("Failed to unlock user.");
            }
            sess.commit();
        } catch (Exception ex) {
            sess.rollback();
            logger.error("Failed to unlock user {}: {}", username, ex.getMessage(), ex);
            throw ex;
        } finally {
            sess.close();
        }
    }
}
*/

package org.fujitsu.training.codes.dao.impl;

import java.util.Collections;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.model.data.User;
import org.springframework.stereotype.Repository;

@Repository
public class AdminUserDaoImpl {
    private static final Logger logger = LogManager.getLogger("admin-flow");

    private final SqlSessionFactory ssf;

    public AdminUserDaoImpl(SqlSessionFactory ssf) {
        this.ssf = ssf;
    }

    public List<User> getAllUsers(String userType) {
    	logger.info("Getting all users");
        try (SqlSession sess = ssf.openSession()) {
        	logger.info("Returning all users");
        	return sess.selectList("org.fujitsu.training.codes.dao.UserDao.selectAllUsers", userType);
        } catch (Exception ex) {
            logger.error("Failed to load users for userType={}: {}", userType, ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    public void lockUser(String username) throws Exception {
        logger.info("Starting user lock. username={}", username);

        SqlSession sess = ssf.openSession();
        try {
            Integer updated = sess.update("org.fujitsu.training.codes.dao.UserDao.lockUser", username);
            if (updated == null || updated != 1) {
                throw new IllegalStateException("Failed to lock user.");
            }

            sess.commit();
            logger.info("User lock completed. username={}", username);
        } catch (Exception ex) {
            sess.rollback();
            logger.error("User lock failed. username={}: {}", username, ex.getMessage(), ex);
            throw ex;
        } finally {
            sess.close();
        }
    }

    public void unlockUser(String username) throws Exception {
        logger.info("Starting user unlock. username={}", username);

        SqlSession sess = ssf.openSession();
        try {
            Integer updated = sess.update("org.fujitsu.training.codes.dao.UserDao.unlockUser", username);
            if (updated == null || updated != 1) {
                throw new IllegalStateException("Failed to unlock user.");
            }

            sess.commit();
            logger.info("User unlock completed. username={}", username);
        } catch (Exception ex) {
            sess.rollback();
            logger.error("User unlock failed. username={}: {}", username, ex.getMessage(), ex);
            throw ex;
        } finally {
            sess.close();
        }
    }
}

