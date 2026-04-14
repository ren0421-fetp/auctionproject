package org.fujitsu.training.codes.dao.impl;

import java.util.Collections;
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
    private static final Logger logger = LogManager.getLogger("admin-flow");

    private final SqlSessionFactory ssf;

    public AdminNewsDaoImpl(SqlSessionFactory ssf) {
        this.ssf = ssf;
    }

    public List<News> getAllNews() {
    	logger.info("Getting all news");
        try (SqlSession sess = ssf.openSession()) {
        	logger.info("Returning all news");
            return sess.selectList("org.fujitsu.training.codes.dao.NewsDao.selectAllNews");
        } catch (Exception ex) {
            logger.error("Failed to load news list: {}", ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    public NewsForm getNewsForm(Integer newsId) {
    	logger.info("Getting news form");
        try (SqlSession sess = ssf.openSession()) {
            News news = sess.selectOne("org.fujitsu.training.codes.dao.NewsDao.selectNewsById", newsId);
            if (news == null) {
                return null;
            }

            NewsForm form = new NewsForm();
            form.setNewsId(news.getNewsId());
            form.setNewsTitle(news.getNewsTitle());
            form.setNewsContent(news.getNewsContent());
            logger.info("Returning news form");
            return form;
        } catch (Exception ex) {
            logger.error("Failed to load news form for newsId={}: {}", newsId, ex.getMessage(), ex);
            return null;
        }
    }

    public void saveNews(NewsForm form) throws Exception {
        logger.info("Starting news save. newsId={}, newsTitle={}",
                form.getNewsId(), form.getNewsTitle());

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

            validateAffectedRow(affected, "Failed to save news.");
            sess.commit();

            logger.info("News save completed. newsId={}, newsTitle={}",
                    form.getNewsId(), form.getNewsTitle());
        } catch (Exception ex) {
            sess.rollback();
            logger.error("News save failed. newsId={}, newsTitle={}: {}",
                    form.getNewsId(), form.getNewsTitle(), ex.getMessage(), ex);
            throw ex;
        } finally {
            sess.close();
        }
    }

    public void deleteNews(Integer newsId) throws Exception {
        logger.info("Starting news delete. newsId={}", newsId);

        SqlSession sess = ssf.openSession();
        try {
            Integer deleted = sess.delete("org.fujitsu.training.codes.dao.NewsDao.deleteNewsById", newsId);
            validateAffectedRow(deleted, "Failed to delete news.");
            sess.commit();

            logger.info("News delete completed. newsId={}", newsId);
        } catch (Exception ex) {
            sess.rollback();
            logger.error("News delete failed. newsId={}: {}", newsId, ex.getMessage(), ex);
            throw ex;
        } finally {
            sess.close();
        }
    }

    private void validateAffectedRow(Integer affected, String errorMessage) {
        if (affected == null || affected != 1) {
            throw new IllegalStateException(errorMessage);
        }
    }
}
