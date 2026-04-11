package org.fujitsu.training.codes.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.model.data.Package;
import org.springframework.stereotype.Repository;

@Repository
public class PackageDaoImpl {
    private static final Logger logger = LogManager.getLogger(PackageDaoImpl.class);
    private final SqlSessionFactory ssf;

    public PackageDaoImpl(SqlSessionFactory ssf) {
        this.ssf = ssf;
    }

    public List<Package> getAvailablePackages() {
        try (SqlSession sess = ssf.openSession()) {
            return sess.selectList("org.fujitsu.training.codes.dao.PackageDao.selectAllPackages");
        }
    }

    public Integer getRemainingBidCount(String username) {
        try (SqlSession sess = ssf.openSession()) {
            return sess.selectOne(
                    "org.fujitsu.training.codes.dao.PackageUserDao.selectRemainingBidCountByUsername",
                    username);
        }
    }

    public void purchasePackage(Integer packageId, String username) throws Exception {
        logger.info("Purchasing package {} for bidder {}", packageId, username);

        SqlSession sess = ssf.openSession();
        try {
            Package selectedPackage = sess.selectOne(
                    "org.fujitsu.training.codes.dao.PackageDao.selectPackageById",
                    packageId);

            if (selectedPackage == null) {
                throw new IllegalArgumentException("Selected package does not exist.");
            }

            int insertedHistory = sess.insert(
                    "org.fujitsu.training.codes.dao.UserPackageDao.insertUserPackage",
                    new java.util.HashMap<String, Object>() {{
                        put("username", username);
                        put("packageId", packageId);
                    }});

            if (insertedHistory != 1) {
                throw new IllegalStateException("Failed to save package purchase history.");
            }

            Integer balanceRowCount = sess.selectOne(
                    "org.fujitsu.training.codes.dao.PackageUserDao.countBalanceRowsByUsername",
                    username);

            int affected;
            if (balanceRowCount != null && balanceRowCount > 0) {
                affected = sess.update(
                        "org.fujitsu.training.codes.dao.PackageUserDao.addBidCountByUsername",
                        new java.util.HashMap<String, Object>() {{
                            put("username", username);
                            put("additionalBidCount", selectedPackage.getAllowedBidCount());
                        }});
            } else {
                affected = sess.insert(
                        "org.fujitsu.training.codes.dao.PackageUserDao.insertBidBalance",
                        new java.util.HashMap<String, Object>() {{
                            put("username", username);
                            put("totalPackageBid", selectedPackage.getAllowedBidCount());
                        }});
            }

            if (affected != 1) {
                throw new IllegalStateException("Failed to update bidder package balance.");
            }

            sess.commit();
            logger.info("Package {} purchased successfully for bidder {}", packageId, username);
        } catch (Exception ex) {
            sess.rollback();
            logger.error("Failed to purchase package {} for bidder {}: {}", packageId, username, ex.getMessage(), ex);
            throw ex;
        } finally {
            sess.close();
        }
    }
}
