package org.fujitsu.training.codes.dao.impl;

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
