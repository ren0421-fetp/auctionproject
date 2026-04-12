package org.fujitsu.training.codes.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.fujitsu.training.codes.model.data.Bid;
import org.fujitsu.training.codes.model.data.ConfirmBidReportRow;
import org.fujitsu.training.codes.model.data.ProductReportRow;
import org.fujitsu.training.codes.model.data.UserReportRow;
import org.springframework.stereotype.Repository;

@Repository
public class AdminReportDaoImpl {
    private final SqlSessionFactory ssf;

    public AdminReportDaoImpl(SqlSessionFactory ssf) {
        this.ssf = ssf;
    }

    public List<UserReportRow> getUserRegistrationReport() {
        try (SqlSession sess = ssf.openSession()) {
            return sess.selectList("org.fujitsu.training.codes.dao.ReportDao.selectUserRegistrationReport");
        }
    }

    public List<ProductReportRow> getAuctionItemReport() {
        try (SqlSession sess = ssf.openSession()) {
            return sess.selectList("org.fujitsu.training.codes.dao.ReportDao.selectAuctionItemReport");
        }
    }

    public List<Bid> getBidReport() {
        try (SqlSession sess = ssf.openSession()) {
            return sess.selectList("org.fujitsu.training.codes.dao.ReportDao.selectBidReport");
        }
    }

    public List<ConfirmBidReportRow> getConfirmBidReport() {
        try (SqlSession sess = ssf.openSession()) {
            return sess.selectList("org.fujitsu.training.codes.dao.ReportDao.selectConfirmBidReport");
        }
    }
}
