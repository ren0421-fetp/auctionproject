/*package org.fujitsu.training.codes.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.fujitsu.training.codes.model.data.Feedback;
import org.fujitsu.training.codes.model.data.News;
import org.fujitsu.training.codes.model.form.FeedbackForm;
import org.springframework.stereotype.Repository;

@Repository
public class FeedbackFlowDaoImpl {
    private final SqlSessionFactory ssf;

    public FeedbackFlowDaoImpl(SqlSessionFactory ssf) {
        this.ssf = ssf;
    }

    public List<News> getAllNews() {
        try (SqlSession sess = ssf.openSession()) {
            return sess.selectList("org.fujitsu.training.codes.dao.NewsDao.selectAllNews");
        }
    }

    public List<Feedback> getAllFeedback() {
        try (SqlSession sess = ssf.openSession()) {
            return sess.selectList("org.fujitsu.training.codes.dao.FeedbackDao.selectAllFeedback");
        }
    }

    public void saveFeedback(FeedbackForm form) throws Exception {
        SqlSession sess = ssf.openSession();
        try {
            Feedback feedback = new Feedback();
            feedback.setFirstName(form.getFirstName().trim());
            feedback.setEmail(form.getEmail().trim());
            feedback.setContact(form.getContact().trim());
            feedback.setSubject(form.getSubject().trim());
            feedback.setMsg(form.getMsg().trim());

            Integer inserted = sess.insert("org.fujitsu.training.codes.dao.FeedbackDao.insertFeedback", feedback);
            if (inserted == null || inserted != 1) {
                throw new IllegalStateException("Failed to save feedback.");
            }

            sess.commit();
        } catch (Exception ex) {
            sess.rollback();
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
import org.fujitsu.training.codes.model.data.Feedback;
import org.fujitsu.training.codes.model.data.News;
import org.fujitsu.training.codes.model.form.FeedbackForm;
import org.springframework.stereotype.Repository;

@Repository
public class FeedbackFlowDaoImpl {
    private static final Logger logger = LogManager.getLogger("common-flow");

    private final SqlSessionFactory ssf;

    public FeedbackFlowDaoImpl(SqlSessionFactory ssf) {
        this.ssf = ssf;
    }

    public List<News> getAllNews() {
    	 logger.info("Getting all news");
        try (SqlSession sess = ssf.openSession()) {
        	logger.info("Returning all news");
            return sess.selectList("org.fujitsu.training.codes.dao.NewsDao.selectAllNews");
        } catch (Exception ex) {
            logger.error("Failed to load news list for feedback flow: {}", ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    public List<Feedback> getAllFeedback() {
    	logger.info("Getting all feedback");
        try (SqlSession sess = ssf.openSession()) {
        	logger.info("Returning all feedback");
            return sess.selectList("org.fujitsu.training.codes.dao.FeedbackDao.selectAllFeedback");
        } catch (Exception ex) {
            logger.error("Failed to load feedback list: {}", ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    public void saveFeedback(FeedbackForm form) throws Exception {
        logger.info("Starting feedback save. subject={}", form.getSubject());

        SqlSession sess = ssf.openSession();
        try {
            Feedback feedback = new Feedback();
            feedback.setFirstName(form.getFirstName().trim());
            feedback.setEmail(form.getEmail().trim());
            feedback.setContact(form.getContact().trim());
            feedback.setSubject(form.getSubject().trim());
            feedback.setMsg(form.getMsg().trim());

            Integer inserted = sess.insert("org.fujitsu.training.codes.dao.FeedbackDao.insertFeedback", feedback);
            if (inserted == null || inserted != 1) {
                throw new IllegalStateException("Failed to save feedback.");
            }

            sess.commit();
            logger.info("Feedback save completed. subject={}", form.getSubject());
        } catch (Exception ex) {
            sess.rollback();
            logger.error("Feedback save failed. subject={}: {}", form.getSubject(), ex.getMessage(), ex);
            throw ex;
        } finally {
            sess.close();
        }
    }
}
