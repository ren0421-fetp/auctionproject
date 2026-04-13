/*package org.fujitsu.training.codes.dao.impl;

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
*/

package org.fujitsu.training.codes.dao.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.model.data.Package;
import org.fujitsu.training.codes.model.data.PackagePurchaseRequest;
import org.springframework.stereotype.Repository;

@Repository
public class PackageDaoImpl {
    private static final Logger logger = LogManager.getLogger("bidder-flow");

    private final SqlSessionFactory ssf;

    public PackageDaoImpl(SqlSessionFactory ssf) {
        this.ssf = ssf;
    }

    public List<Package> getAvailablePackages() {
        logger.info("Getting all available packages.");
        try (SqlSession sess = ssf.openSession()) {
            List<Package> packages = sess.selectList("org.fujitsu.training.codes.dao.PackageDao.selectAllPackages");
            logger.info("Returning all available packages.");
            return packages;
        } catch (Exception ex) {
            logger.error("Failed to load available packages: {}", ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    public Integer getRemainingBidCount(String username) {
        logger.info("Getting remaining bid count for username={}", username);
        try (SqlSession sess = ssf.openSession()) {
            Integer remainingBidCount = sess.selectOne(
                    "org.fujitsu.training.codes.dao.PackageUserDao.selectRemainingBidCountByUsername",
                    username);
            logger.info("Returning remaining bid count for username={}", username);
            return remainingBidCount;
        } catch (Exception ex) {
            logger.error("Failed to load remaining bid count for username={}: {}", username, ex.getMessage(), ex);
            return null;
        }
    }

    public void purchasePackage(Integer packageId, String username) throws Exception {
        logger.info("Starting package purchase request. packageId={}, username={}", packageId, username);

        SqlSession sess = ssf.openSession();
        try {
            Package selectedPackage = sess.selectOne(
                    "org.fujitsu.training.codes.dao.PackageDao.selectPackageById",
                    packageId);

            if (selectedPackage == null) {
                throw new IllegalArgumentException("Selected package does not exist.");
            }

            Integer pendingRequestCount = sess.selectOne(
                    "org.fujitsu.training.codes.dao.PackagePurchaseRequestDao.countPendingRequestsByUsername",
                    username);

            if (pendingRequestCount != null && pendingRequestCount > 0) {
                throw new IllegalArgumentException("You already have a pending package request.");
            }

            PackagePurchaseRequest request = new PackagePurchaseRequest();
            request.setUsername(username);
            request.setPackageId(packageId);
            request.setRequestStatus("PENDING");
            request.setRequestedAt(LocalDateTime.now());
            request.setRemarks(null);

            Integer inserted = sess.insert(
                    "org.fujitsu.training.codes.dao.PackagePurchaseRequestDao.insertRequest",
                    request);

            if (inserted == null || inserted != 1) {
                throw new IllegalStateException("Failed to submit package purchase request.");
            }

            sess.commit();
            logger.info("Package purchase request completed. packageId={}, username={}", packageId, username);
        } catch (Exception ex) {
            sess.rollback();
            logger.error("Package purchase request failed. packageId={}, username={}: {}",
                    packageId, username, ex.getMessage(), ex);
            throw ex;
        } finally {
            sess.close();
        }
    }
}
