/*package org.fujitsu.training.codes.dao.impl;

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
*/

package org.fujitsu.training.codes.dao.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.model.data.Package;
import org.fujitsu.training.codes.model.data.PackagePurchaseRequest;
import org.fujitsu.training.codes.model.data.PackagePurchaseRequestInfo;
import org.fujitsu.training.codes.model.data.User;
import org.fujitsu.training.codes.model.data.UserPackageInfo;
import org.fujitsu.training.codes.model.form.PackagePurchaseRequestReviewForm;
import org.fujitsu.training.codes.model.form.UserPackageForm;
import org.springframework.stereotype.Repository;

@Repository
public class AdminUserPackageDaoImpl {
    private static final Logger logger = LogManager.getLogger("admin-flow");

    private final SqlSessionFactory ssf;

    public AdminUserPackageDaoImpl(SqlSessionFactory ssf) {
        this.ssf = ssf;
    }

    public List<User> getBidders() {
        logger.info("Getting bidders.");
        try (SqlSession sess = ssf.openSession()) {
            return sess.selectList("org.fujitsu.training.codes.dao.UserDao.selectUsersByType", "bidder");
        } catch (Exception ex) {
            logger.error("Failed to load bidders: {}", ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    public List<Package> getPackages() {
        logger.info("Getting packages.");
        try (SqlSession sess = ssf.openSession()) {
            return sess.selectList("org.fujitsu.training.codes.dao.PackageDao.selectAllPackages");
        } catch (Exception ex) {
            logger.error("Failed to load packages: {}", ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    public List<UserPackageInfo> getAllUserPackageInfos() {
        logger.info("Getting all granted user package infos.");
        try (SqlSession sess = ssf.openSession()) {
            return sess.selectList("org.fujitsu.training.codes.dao.UserPackageDao.selectAllUserPackageInfos");
        } catch (Exception ex) {
            logger.error("Failed to load user package infos: {}", ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    public List<PackagePurchaseRequestInfo> getAllPackagePurchaseRequestInfos() {
        logger.info("Getting all package purchase request infos.");
        try (SqlSession sess = ssf.openSession()) {
            return sess.selectList("org.fujitsu.training.codes.dao.PackagePurchaseRequestDao.selectAllRequestInfos");
        } catch (Exception ex) {
            logger.error("Failed to load package purchase request infos: {}", ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    public UserPackageForm getUserPackageForm(Integer userPackageId) {
        logger.info("Getting user package form. userPackageId={}", userPackageId);
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
        } catch (Exception ex) {
            logger.error("Failed to load user package form for userPackageId={}: {}", userPackageId, ex.getMessage(), ex);
            return null;
        }
    }

    public void assignPackage(UserPackageForm form) throws Exception {
        logger.info("Starting manual package assignment. username={}, packageId={}",
                form.getUsername(), form.getPackageId());

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

            grantPackage(sess, form.getUsername(), pkg);
            sess.commit();

            logger.info("Manual package assignment completed. username={}, packageId={}",
                    form.getUsername(), form.getPackageId());
        } catch (Exception ex) {
            sess.rollback();
            logger.error("Manual package assignment failed. username={}, packageId={}: {}",
                    form.getUsername(), form.getPackageId(), ex.getMessage(), ex);
            throw ex;
        } finally {
            sess.close();
        }
    }

    public void approvePackageRequest(PackagePurchaseRequestReviewForm form, String adminUsername) throws Exception {
        logger.info("Starting package request approval. requestId={}, adminUsername={}",
                form.getRequestId(), adminUsername);

        SqlSession sess = ssf.openSession();
        try {
            PackagePurchaseRequest request = sess.selectOne(
                    "org.fujitsu.training.codes.dao.PackagePurchaseRequestDao.selectRequestById",
                    form.getRequestId());

            if (request == null) {
                throw new IllegalArgumentException("Package purchase request not found.");
            }

            if (!"PENDING".equalsIgnoreCase(request.getRequestStatus())) {
                throw new IllegalArgumentException("Only pending requests can be approved.");
            }

            Package pkg = sess.selectOne(
                    "org.fujitsu.training.codes.dao.PackageDao.selectPackageById",
                    request.getPackageId());

            if (pkg == null) {
                throw new IllegalArgumentException("Requested package does not exist.");
            }

            Integer reviewed = sess.update(
                    "org.fujitsu.training.codes.dao.PackagePurchaseRequestDao.reviewRequest",
                    createReviewParams(form.getRequestId(), "APPROVED", adminUsername, form.getRemarks()));

            if (reviewed == null || reviewed != 1) {
                throw new IllegalStateException("Failed to approve package purchase request.");
            }

            grantPackage(sess, request.getUsername(), pkg);
            sess.commit();

            logger.info("Package request approval completed. requestId={}, adminUsername={}",
                    form.getRequestId(), adminUsername);
        } catch (Exception ex) {
            sess.rollback();
            logger.error("Package request approval failed. requestId={}, adminUsername={}: {}",
                    form.getRequestId(), adminUsername, ex.getMessage(), ex);
            throw ex;
        } finally {
            sess.close();
        }
    }

    public void rejectPackageRequest(PackagePurchaseRequestReviewForm form, String adminUsername) throws Exception {
        logger.info("Starting package request rejection. requestId={}, adminUsername={}",
                form.getRequestId(), adminUsername);

        SqlSession sess = ssf.openSession();
        try {
            PackagePurchaseRequest request = sess.selectOne(
                    "org.fujitsu.training.codes.dao.PackagePurchaseRequestDao.selectRequestById",
                    form.getRequestId());

            if (request == null) {
                throw new IllegalArgumentException("Package purchase request not found.");
            }

            if (!"PENDING".equalsIgnoreCase(request.getRequestStatus())) {
                throw new IllegalArgumentException("Only pending requests can be rejected.");
            }

            Integer reviewed = sess.update(
                    "org.fujitsu.training.codes.dao.PackagePurchaseRequestDao.reviewRequest",
                    createReviewParams(form.getRequestId(), "REJECTED", adminUsername, form.getRemarks()));

            if (reviewed == null || reviewed != 1) {
                throw new IllegalStateException("Failed to reject package purchase request.");
            }

            sess.commit();
            logger.info("Package request rejection completed. requestId={}, adminUsername={}",
                    form.getRequestId(), adminUsername);
        } catch (Exception ex) {
            sess.rollback();
            logger.error("Package request rejection failed. requestId={}, adminUsername={}: {}",
                    form.getRequestId(), adminUsername, ex.getMessage(), ex);
            throw ex;
        } finally {
            sess.close();
        }
    }

    private void grantPackage(SqlSession sess, String username, Package pkg) {
        Map<String, Object> assignParams = new HashMap<>();
        assignParams.put("username", username);
        assignParams.put("packageId", pkg.getPackageId());

        Integer insertedHistory = sess.insert(
                "org.fujitsu.training.codes.dao.UserPackageDao.insertUserPackage",
                assignParams);

        if (insertedHistory == null || insertedHistory != 1) {
            throw new IllegalStateException("Failed to save granted user package record.");
        }

        Integer balanceRowCount = sess.selectOne(
                "org.fujitsu.training.codes.dao.PackageUserDao.countBalanceRowsByUsername",
                username);

        Map<String, Object> balanceParams = new HashMap<>();
        balanceParams.put("username", username);
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
    }

    private Map<String, Object> createReviewParams(Integer requestId, String status,
            String adminUsername, String remarks) {
        Map<String, Object> params = new HashMap<>();
        params.put("requestId", requestId);
        params.put("requestStatus", status);
        params.put("reviewedAt", LocalDateTime.now());
        params.put("reviewedBy", adminUsername);
        params.put("remarks", remarks);
        return params;
    }
}

