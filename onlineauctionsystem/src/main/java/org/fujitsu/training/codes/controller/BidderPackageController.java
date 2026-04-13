/*package org.fujitsu.training.codes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.PackageDaoImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/bidder/package")
public class BidderPackageController {
    private static final Logger logger = LogManager.getLogger(BidderPackageController.class);

    private final PackageDaoImpl bidderPackageDaoImpl;

    public BidderPackageController(PackageDaoImpl bidderPackageDaoImpl) {
        this.bidderPackageDaoImpl = bidderPackageDaoImpl;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String loadPackages(
            @RequestParam(value = "success", required = false) String success,
            Model model,
            HttpSession session) {

        String bidderUsername = getBidderUsername(session);
        if (bidderUsername == null) {
            return "redirect:/app/login";
        }

        model.addAttribute("availablePackages", bidderPackageDaoImpl.getAvailablePackages());
        model.addAttribute("remainingBidCount", bidderPackageDaoImpl.getRemainingBidCount(bidderUsername));

        if ("1".equals(success)) {
            model.addAttribute("purchaseSuccess", "Package purchased successfully.");
        }

        return "bidderPackageListView";
    }

    @RequestMapping(value = "/purchase", method = RequestMethod.POST)
    public String purchasePackage(
            @RequestParam("packageId") Integer packageId,
            Model model,
            HttpSession session) {

        String bidderUsername = getBidderUsername(session);
        if (bidderUsername == null) {
            return "redirect:/app/login";
        }

        try {
            bidderPackageDaoImpl.purchasePackage(packageId, bidderUsername);
            return "redirect:/app/bidder/package/list?success=1";
        } catch (Exception ex) {
            logger.error("Package purchase failed for bidder {}: {}", bidderUsername, ex.getMessage(), ex);
            model.addAttribute("availablePackages", bidderPackageDaoImpl.getAvailablePackages());
            model.addAttribute("remainingBidCount", bidderPackageDaoImpl.getRemainingBidCount(bidderUsername));
            model.addAttribute("purchaseError", ex.getMessage());
            return "bidderPackageListView";
        }
    }

    private String getBidderUsername(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUsername");
        String userType = (String) session.getAttribute("loggedInUserType");

        if (username == null || userType == null || !"bidder".equalsIgnoreCase(userType)) {
            return null;
        }
        return username;
    }
}*/

package org.fujitsu.training.codes.controller;

import org.fujitsu.training.codes.helper.BidderPackageHelper;
import org.fujitsu.training.codes.helper.SessionRoleHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/bidder/package")
public class BidderPackageController {

    private static final String LOGIN_REDIRECT = "redirect:/app/login";
    private static final String VIEW_NAME = "bidderPackageListView";

    private final BidderPackageHelper bidderPackageHelper;
    private final SessionRoleHelper sessionRoleHelper;

    public BidderPackageController(BidderPackageHelper bidderPackageHelper,
            SessionRoleHelper sessionRoleHelper) {
        this.bidderPackageHelper = bidderPackageHelper;
        this.sessionRoleHelper = sessionRoleHelper;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String loadPackages(
            @RequestParam(value = "success", required = false) String success,
            Model model,
            HttpSession session) {

        String bidderUsername = sessionRoleHelper.getBidderUsername(session);
        if (bidderUsername == null) {
            return LOGIN_REDIRECT;
        }

        bidderPackageHelper.prepareLoadPackages(success, bidderUsername, model);
        return VIEW_NAME;
    }

    @RequestMapping(value = "/purchase", method = RequestMethod.POST)
    public String purchasePackage(
            @RequestParam("packageId") Integer packageId,
            Model model,
            HttpSession session) {

        String bidderUsername = sessionRoleHelper.getBidderUsername(session);
        if (bidderUsername == null) {
            return LOGIN_REDIRECT;
        }

        return bidderPackageHelper.processPurchasePackage(packageId, bidderUsername, model);
    }
}
