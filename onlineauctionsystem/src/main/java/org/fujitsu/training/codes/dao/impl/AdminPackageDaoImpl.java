package org.fujitsu.training.codes.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.model.data.Package;
import org.fujitsu.training.codes.model.form.PackageForm;
import org.springframework.stereotype.Repository;

@Repository
public class AdminPackageDaoImpl {
    private static final Logger logger = LogManager.getLogger(AdminPackageDaoImpl.class);
    private final SqlSessionFactory ssf;

    public AdminPackageDaoImpl(SqlSessionFactory ssf) {
        this.ssf = ssf;
    }

    public List<Package> getAllPackages() {
        try (SqlSession sess = ssf.openSession()) {
            return sess.selectList("org.fujitsu.training.codes.dao.PackageDao.selectAllPackages");
        }
    }

    public PackageForm getPackageForm(Integer packageId) {
        try (SqlSession sess = ssf.openSession()) {
            Package pkg = sess.selectOne("org.fujitsu.training.codes.dao.PackageDao.selectPackageById", packageId);
            if (pkg == null) {
                return null;
            }

            PackageForm form = new PackageForm();
            form.setPackageId(pkg.getPackageId());
            form.setPackageName(pkg.getPackageName());
            form.setPackagePrice(pkg.getPackagePrice());
            form.setAllowedBidCount(pkg.getAllowedBidCount());
            form.setPhotoPath(pkg.getPhotoPath());
            form.setCurrentPhotoPath(pkg.getPhotoPath());
            return form;

        }
    }

    public void savePackage(PackageForm form) throws Exception {
        logger.info("Saving package: {}", form.getPackageName());

        SqlSession sess = ssf.openSession();
        try {
            java.util.Map<String, Object> duplicateParams = new java.util.HashMap<>();
            duplicateParams.put("packageName", form.getPackageName());
            duplicateParams.put("packageId", form.getPackageId());

            Integer duplicateCount = sess.selectOne(
                    "org.fujitsu.training.codes.dao.PackageDao.countPackageByName",
                    duplicateParams);

            if (duplicateCount != null && duplicateCount > 0) {
                throw new IllegalArgumentException("Package name already exists.");
            }

            Package pkg = new Package();
            pkg.setPackageId(form.getPackageId());
            pkg.setPackageName(form.getPackageName().trim());
            pkg.setPackagePrice(form.getPackagePrice());
            pkg.setAllowedBidCount(form.getAllowedBidCount());
            pkg.setPhotoPath(form.getPhotoPath());

            if (form.getPackageId() == null) {
                Integer inserted = sess.insert("org.fujitsu.training.codes.dao.PackageDao.insertPackage", pkg);
                if (inserted == null || inserted != 1) {
                    throw new IllegalStateException("Failed to add package.");
                }
            } else {
                Integer updated = sess.update("org.fujitsu.training.codes.dao.PackageDao.updatePackage", pkg);
                if (updated == null || updated != 1) {
                    throw new IllegalStateException("Failed to update package.");
                }
            }

            sess.commit();
        } catch (Exception ex) {
            sess.rollback();
            logger.error("Failed to save package {}: {}", form.getPackageName(), ex.getMessage(), ex);
            throw ex;
        } finally {
            sess.close();
        }
    }

    public void deletePackage(Integer packageId) throws Exception {
        logger.info("Deleting package {}", packageId);

        SqlSession sess = ssf.openSession();
        try {
            Integer usageCount = sess.selectOne(
                    "org.fujitsu.training.codes.dao.PackageDao.countUsersUsingPackage",
                    packageId);

            if (usageCount != null && usageCount > 0) {
                throw new IllegalArgumentException("Package cannot be deleted because users are already assigned to it.");
            }

            Integer deleted = sess.delete("org.fujitsu.training.codes.dao.PackageDao.deletePackageById", packageId);
            if (deleted == null || deleted != 1) {
                throw new IllegalStateException("Failed to delete package.");
            }

            sess.commit();
        } catch (Exception ex) {
            sess.rollback();
            logger.error("Failed to delete package {}: {}", packageId, ex.getMessage(), ex);
            throw ex;
        } finally {
            sess.close();
        }
    }
}
