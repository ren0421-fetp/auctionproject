package org.fujitsu.training.codes.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.model.data.News;
import org.fujitsu.training.codes.model.form.NewsForm;
import org.springframework.stereotype.Repository;

@Repository
public class AdminNewsDaoImpl {
    private static final Logger logger = LogManager.getLogger(AdminNewsDaoImpl.class);
    private final SqlSessionFactory ssf;

    public AdminNewsDaoImpl(SqlSessionFactory ssf) {
        this.ssf = ssf;
    }

    public List<News> getAllNews() {
        try (SqlSession sess = ssf.openSession()) {
            return sess.selectList("org.fujitsu.training.codes.dao.NewsDao.selectAllNews");
        }
    }

    public NewsForm getNewsForm(Integer newsId) {
        try (SqlSession sess = ssf.openSession()) {
            News news = sess.selectOne("org.fujitsu.training.codes.dao.NewsDao.selectNewsById", newsId);
            if (news == null) {
                return null;
            }

            NewsForm form = new NewsForm();
            form.setNewsId(news.getNewsId());
            form.setNewsTitle(news.getNewsTitle());
            form.setNewsContent(news.getNewsContent());
            return form;
        }
    }

    public void saveNews(NewsForm form) throws Exception {
        logger.info("Saving news {}", form.getNewsTitle());

        SqlSession sess = ssf.openSession();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("newsTitle", form.getNewsTitle());
            params.put("newsId", form.getNewsId());

            Integer duplicateCount = sess.selectOne(
                    "org.fujitsu.training.codes.dao.NewsDao.countNewsByTitle",
                    params);

            if (duplicateCount != null && duplicateCount > 0) {
                throw new IllegalArgumentException("News title already exists.");
            }

            News news = new News();
            news.setNewsId(form.getNewsId());
            news.setNewsTitle(form.getNewsTitle().trim());
            news.setNewsContent(form.getNewsContent().trim());

            Integer affected;
            if (form.getNewsId() == null) {
                affected = sess.insert("org.fujitsu.training.codes.dao.NewsDao.insertNews", news);
            } else {
                affected = sess.update("org.fujitsu.training.codes.dao.NewsDao.updateNews", news);
            }

            if (affected == null || affected != 1) {
                throw new IllegalStateException("Failed to save news.");
            }

            sess.commit();
        } catch (Exception ex) {
            sess.rollback();
            throw ex;
        } finally {
            sess.close();
        }
    }

    public void deleteNews(Integer newsId) throws Exception {
        SqlSession sess = ssf.openSession();
        try {
            Integer deleted = sess.delete("org.fujitsu.training.codes.dao.NewsDao.deleteNewsById", newsId);
            if (deleted == null || deleted != 1) {
                throw new IllegalStateException("Failed to delete news.");
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
