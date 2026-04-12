package org.fujitsu.training.codes.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.model.data.Package;
import org.fujitsu.training.codes.model.data.User;
import org.fujitsu.training.codes.model.data.UserPackageInfo;
import org.fujitsu.training.codes.model.form.UserPackageForm;
import org.springframework.stereotype.Repository;

@Repository
public class AdminUserPackageDaoImpl {
    private static final Logger logger = LogManager.getLogger(AdminUserPackageDaoImpl.class);
    private final SqlSessionFactory ssf;

    public AdminUserPackageDaoImpl(SqlSessionFactory ssf) {
        this.ssf = ssf;
    }

    public List<User> getBidders() {
        try (SqlSession sess = ssf.openSession()) {
            return sess.selectList("org.fujitsu.training.codes.dao.UserDao.selectUsersByType", "bidder");
        }
    }

    public List<Package> getPackages() {
        try (SqlSession sess = ssf.openSession()) {
            return sess.selectList("org.fujitsu.training.codes.dao.PackageDao.selectAllPackages");
        }
    }

    public List<UserPackageInfo> getAllUserPackageInfos() {
        try (SqlSession sess = ssf.openSession()) {
            return sess.selectList("org.fujitsu.training.codes.dao.UserPackageDao.selectAllUserPackageInfos");
        }
    }

    public UserPackageForm getUserPackageForm(Integer userPackageId) {
        try (SqlSession sess = ssf.openSession()) {
            UserPackageInfo info = sess.selectOne(
                    "org.fujitsu.training.codes.dao.UserPackageDao.selectUserPackageInfoById",
                    userPackageId);

            if (info == null) {
                return null;
            }

            UserPackageForm form = new UserPackageForm();
            form.setUsername(info.getUsername());
            form.setPackageId(info.getPackageId());
            return form;
        }
    }

    public void assignPackage(UserPackageForm form) throws Exception {
        logger.info("Assigning package {} to bidder {}", form.getPackageId(), form.getUsername());

        SqlSession sess = ssf.openSession();
        try {
            User bidder = sess.selectOne("org.fujitsu.training.codes.dao.UserDao.selectByUsername", form.getUsername());
            if (bidder == null || !"bidder".equalsIgnoreCase(bidder.getUserType())) {
                throw new IllegalArgumentException("Selected user is not a valid bidder.");
            }

            Package pkg = sess.selectOne("org.fujitsu.training.codes.dao.PackageDao.selectPackageById", form.getPackageId());
            if (pkg == null) {
                throw new IllegalArgumentException("Selected package does not exist.");
            }

            java.util.Map<String, Object> assignParams = new java.util.HashMap<>();
            assignParams.put("username", form.getUsername());
            assignParams.put("packageId", form.getPackageId());

            Integer insertedHistory = sess.insert(
                    "org.fujitsu.training.codes.dao.UserPackageDao.insertUserPackage",
                    assignParams);

            if (insertedHistory == null || insertedHistory != 1) {
                throw new IllegalStateException("Failed to save user package assignment.");
            }

            Integer balanceRowCount = sess.selectOne(
                    "org.fujitsu.training.codes.dao.PackageUserDao.countBalanceRowsByUsername",
                    form.getUsername());

            java.util.Map<String, Object> balanceParams = new java.util.HashMap<>();
            balanceParams.put("username", form.getUsername());
            balanceParams.put("additionalBidCount", pkg.getAllowedBidCount());
            balanceParams.put("totalPackageBid", pkg.getAllowedBidCount());

            Integer affected;
            if (balanceRowCount != null && balanceRowCount > 0) {
                affected = sess.update(
                        "org.fujitsu.training.codes.dao.PackageUserDao.addBidCountByUsername",
                        balanceParams);
            } else {
                affected = sess.insert(
                        "org.fujitsu.training.codes.dao.PackageUserDao.insertBidBalance",
                        balanceParams);
            }

            if (affected == null || affected != 1) {
                throw new IllegalStateException("Failed to update bidder package balance.");
            }

            sess.commit();
        } catch (Exception ex) {
            sess.rollback();
            logger.error("Failed to assign package {} to bidder {}: {}", form.getPackageId(),
                    form.getUsername(), ex.getMessage(), ex);
            throw ex;
        } finally {
            sess.close();
        }
    }
}
