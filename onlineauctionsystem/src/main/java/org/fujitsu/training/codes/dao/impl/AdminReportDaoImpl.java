package org.fujitsu.training.codes.dao.impl;

import java.util.Collections;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.model.data.Bid;
import org.fujitsu.training.codes.model.data.ConfirmBidReportRow;
import org.fujitsu.training.codes.model.data.ProductReportRow;
import org.fujitsu.training.codes.model.data.UserReportRow;
import org.springframework.stereotype.Repository;

@Repository
public class AdminReportDaoImpl {
    private static final Logger logger = LogManager.getLogger("admin-flow");

    private final SqlSessionFactory ssf;

    public AdminReportDaoImpl(SqlSessionFactory ssf) {
        this.ssf = ssf;
    }

    public List<UserReportRow> getUserRegistrationReport() {
    	logger.info("Getting user registration report");
        try (SqlSession sess = ssf.openSession()) {
        	logger.info("Returning user registration report");
            return sess.selectList("org.fujitsu.training.codes.dao.ReportDao.selectUserRegistrationReport");
        } catch (Exception ex) {
            logger.error("Failed to load user registration report: {}", ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    public List<ProductReportRow> getAuctionItemReport() {
    	logger.info("Getting auction report");
        try (SqlSession sess = ssf.openSession()) {
        	logger.info("Returning auction report");
        	return sess.selectList("org.fujitsu.training.codes.dao.ReportDao.selectAuctionItemReport");
        } catch (Exception ex) {
            logger.error("Failed to load auction item report: {}", ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    public List<Bid> getBidReport() {
    	logger.info("Getting bid report");
        try (SqlSession sess = ssf.openSession()) {
        	logger.info("Returning bid report");
            return sess.selectList("org.fujitsu.training.codes.dao.ReportDao.selectBidReport");
        } catch (Exception ex) {
            logger.error("Failed to load bid report: {}", ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    public List<ConfirmBidReportRow> getConfirmBidReport() {
    	logger.info("Getting confirmed bids report");
        try (SqlSession sess = ssf.openSession()) {
        	logger.info("Returning confirmed bids report");
            return sess.selectList("org.fujitsu.training.codes.dao.ReportDao.selectConfirmBidReport");
        } catch (Exception ex) {
            logger.error("Failed to load confirmed bid report: {}", ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }
}
