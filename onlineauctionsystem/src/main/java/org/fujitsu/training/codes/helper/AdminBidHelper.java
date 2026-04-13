/*package org.fujitsu.training.codes.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.AdminBidDaoImpl;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class AdminBidHelper {
    private static final Logger logger = LogManager.getLogger(AdminBidHelper.class);

    private static final String VIEW_NAME = "adminBidConfirmationView";
    private static final String SUCCESS_REDIRECT = "redirect:/app/admin/bids/confirmations?success=1";

    private final AdminBidDaoImpl adminBidDaoImpl;

    public AdminBidHelper(AdminBidDaoImpl adminBidDaoImpl) {
        this.adminBidDaoImpl = adminBidDaoImpl;
    }

    public void prepareBidConfirmations(String success, Model model) {
        model.addAttribute("allBids", adminBidDaoImpl.getAllBidsForAdmin());

        if ("1".equals(success)) {
            model.addAttribute("confirmSuccess", "Winning bid confirmed and product closed successfully.");
        }
    }

    public String processConfirmBid(Integer bidId, String adminUsername, Model model) {
        try {
            adminBidDaoImpl.confirmWinningBid(bidId);
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Admin {} failed to confirm bid {}: {}", adminUsername, bidId, ex.getMessage(), ex);
            model.addAttribute("allBids", adminBidDaoImpl.getAllBidsForAdmin());
            model.addAttribute("confirmError", ex.getMessage());
            return VIEW_NAME;
        }
    }
}
*/

package org.fujitsu.training.codes.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.AdminBidDaoImpl;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class AdminBidHelper {
    private static final Logger logger = LogManager.getLogger("admin-flow");

    private static final String VIEW_NAME = "adminBidConfirmationView";
    private static final String SUCCESS_REDIRECT = "redirect:/app/admin/bids/confirmations?success=1";

    private final AdminBidDaoImpl adminBidDaoImpl;

    public AdminBidHelper(AdminBidDaoImpl adminBidDaoImpl) {
        this.adminBidDaoImpl = adminBidDaoImpl;
    }

    public void prepareBidConfirmations(String success, Model model) {
        logger.info("Loading admin bid confirmations page.");
        model.addAttribute("allBids", adminBidDaoImpl.getAllBidsForAdmin());

        if ("1".equals(success)) {
            model.addAttribute("confirmSuccess", "Winning bid confirmed and product closed successfully.");
        }

        logger.info("Admin bid confirmations page loaded.");
    }

    public String processConfirmBid(Integer bidId, String adminUsername, Model model) {
        logger.info("Processing winning bid confirmation. adminUsername={}, bidId={}", adminUsername, bidId);
        try {
            adminBidDaoImpl.confirmWinningBid(bidId);
            logger.info("Winning bid confirmation completed. adminUsername={}, bidId={}", adminUsername, bidId);
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Admin {} failed to confirm bid {}: {}", adminUsername, bidId, ex.getMessage(), ex);
            model.addAttribute("allBids", adminBidDaoImpl.getAllBidsForAdmin());
            model.addAttribute("confirmError", ex.getMessage());
            return VIEW_NAME;
        }
    }
}
