/*package org.fujitsu.training.codes.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.PackageDaoImpl;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class BidderPackageHelper {
    private static final Logger logger = LogManager.getLogger(BidderPackageHelper.class);

    private static final String VIEW_NAME = "bidderPackageListView";
    private static final String SUCCESS_REDIRECT = "redirect:/app/bidder/package/list?success=1";

    private final PackageDaoImpl packageDaoImpl;

    public BidderPackageHelper(PackageDaoImpl packageDaoImpl) {
        this.packageDaoImpl = packageDaoImpl;
    }

    public void prepareLoadPackages(String success, String bidderUsername, Model model) {
        model.addAttribute("availablePackages", packageDaoImpl.getAvailablePackages());
        model.addAttribute("remainingBidCount", packageDaoImpl.getRemainingBidCount(bidderUsername));

        if ("1".equals(success)) {
            model.addAttribute("purchaseSuccess", "Package purchased successfully.");
        }
    }

    public String processPurchasePackage(Integer packageId, String bidderUsername, Model model) {
        try {
            packageDaoImpl.purchasePackage(packageId, bidderUsername);
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Package purchase failed for bidder {}: {}", bidderUsername, ex.getMessage(), ex);
            model.addAttribute("availablePackages", packageDaoImpl.getAvailablePackages());
            model.addAttribute("remainingBidCount", packageDaoImpl.getRemainingBidCount(bidderUsername));
            model.addAttribute("purchaseError", ex.getMessage());
            return VIEW_NAME;
        }
    }
}
*/

package org.fujitsu.training.codes.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.PackageDaoImpl;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class BidderPackageHelper {
    private static final Logger logger = LogManager.getLogger("bidder-flow");

    private static final String VIEW_NAME = "bidderPackageListView";
    private static final String SUCCESS_REDIRECT = "redirect:/app/bidder/package/list?success=1";

    private final PackageDaoImpl packageDaoImpl;

    public BidderPackageHelper(PackageDaoImpl packageDaoImpl) {
        this.packageDaoImpl = packageDaoImpl;
    }

    public void prepareLoadPackages(String success, String bidderUsername, Model model) {
        logger.info("Loading bidder package page. bidderUsername={}", bidderUsername);

        model.addAttribute("availablePackages", packageDaoImpl.getAvailablePackages());
        model.addAttribute("remainingBidCount", packageDaoImpl.getRemainingBidCount(bidderUsername));

        if ("1".equals(success)) {
            model.addAttribute("purchaseSuccess", "Package purchased successfully.");
        }

        logger.info("Bidder package page loaded. bidderUsername={}", bidderUsername);
    }

    public String processPurchasePackage(Integer packageId, String bidderUsername, Model model) {
        logger.info("Processing package purchase. bidderUsername={}, packageId={}", bidderUsername, packageId);
        try {
            packageDaoImpl.purchasePackage(packageId, bidderUsername);
            logger.info("Package purchase completed. bidderUsername={}, packageId={}", bidderUsername, packageId);
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Package purchase failed for bidder {}: {}", bidderUsername, ex.getMessage(), ex);
            model.addAttribute("availablePackages", packageDaoImpl.getAvailablePackages());
            model.addAttribute("remainingBidCount", packageDaoImpl.getRemainingBidCount(bidderUsername));
            model.addAttribute("purchaseError", ex.getMessage());
            return VIEW_NAME;
        }
    }
}
