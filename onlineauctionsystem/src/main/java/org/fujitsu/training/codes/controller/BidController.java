/*package org.fujitsu.training.codes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.BidDaoImpl;
import org.fujitsu.training.codes.model.data.Product;
import org.fujitsu.training.codes.model.form.BidForm;
import org.fujitsu.training.codes.validator.BidFormValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/bidder/auctions")
public class BidController {
    private static final Logger logger = LogManager.getLogger(BidController.class);

    private final BidDaoImpl bidderAuctionDaoImpl;
    private final BidFormValidator bidFormValidator;

    public BidController(BidDaoImpl bidderAuctionDaoImpl,
            BidFormValidator bidFormValidator) {
        this.bidderAuctionDaoImpl = bidderAuctionDaoImpl;
        this.bidFormValidator = bidFormValidator;
    }

    @InitBinder("bidForm")
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(bidFormValidator);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String loadOpenProducts(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "catId", required = false) Integer catId,
            @RequestParam(value = "minPrice", required = false) java.math.BigDecimal minPrice,
            @RequestParam(value = "maxPrice", required = false) java.math.BigDecimal maxPrice,
            Model model,
            HttpSession session) {

        String bidderUsername = getBidderUsername(session);
        if (bidderUsername == null) {
            return "redirect:/app/login";
        }

        model.addAttribute("openProducts",
                bidderAuctionDaoImpl.getOpenProducts(keyword, catId, minPrice, maxPrice));
        model.addAttribute("categoryOpts", bidderAuctionDaoImpl.getCategories());
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedCatId", catId);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        return "bidderProductListView";
    }



    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String loadProductDetail(
            @RequestParam("productId") Integer productId,
            @RequestParam(value = "success", required = false) String success,
            Model model,
            HttpSession session) {

        String bidderUsername = getBidderUsername(session);
        if (bidderUsername == null) {
            return "redirect:/app/login";
        }

        Product product = bidderAuctionDaoImpl.getProductDetail(productId);
        if (product == null) {
            return "redirect:/app/bidder/auctions/list";
        }

        BidForm bidForm = new BidForm();
        bidForm.setProductId(productId);

        model.addAttribute("product", product);
        model.addAttribute("bidForm", bidForm);
        model.addAttribute("remainingBidCount", bidderAuctionDaoImpl.getRemainingBidCount(bidderUsername));

        if ("1".equals(success)) {
            model.addAttribute("bidSuccess", "Bid placed successfully.");
        }

        return "bidderProductDetailView";
    }

    @RequestMapping(value = "/bid", method = RequestMethod.POST)
    public String submitBid(
            @Validated @ModelAttribute("bidForm") BidForm bidForm,
            BindingResult result,
            Model model,
            HttpSession session) {

        String bidderUsername = getBidderUsername(session);
        if (bidderUsername == null) {
            return "redirect:/app/login";
        }

        Product product = bidderAuctionDaoImpl.getProductDetail(bidForm.getProductId());
        if (product == null) {
            return "redirect:/app/bidder/auctions/list";
        }

        if (result.hasErrors()) {
            model.addAttribute("product", product);
            model.addAttribute("remainingBidCount", bidderAuctionDaoImpl.getRemainingBidCount(bidderUsername));
            return "bidderProductDetailView";
        }

        try {
            bidderAuctionDaoImpl.placeBid(bidForm, bidderUsername);
            return "redirect:/app/bidder/auctions/detail?productId=" + bidForm.getProductId() + "&success=1";
        } catch (Exception ex) {
            logger.error("Bid submission failed for bidder {}: {}", bidderUsername, ex.getMessage(), ex);
            model.addAttribute("product", product);
            model.addAttribute("remainingBidCount", bidderAuctionDaoImpl.getRemainingBidCount(bidderUsername));
            model.addAttribute("bidError", ex.getMessage());
            return "bidderProductDetailView";
        }
    }
    
    @RequestMapping(value = "/my-bids", method = RequestMethod.GET)
    public String loadMyBids(Model model, HttpSession session) {
        String bidderUsername = getBidderUsername(session);
        if (bidderUsername == null) {
            return "redirect:/app/login";
        }

        model.addAttribute("myBids", bidderAuctionDaoImpl.getBidderBids(bidderUsername));
        return "bidderMyBidsView";
    }

    private String getBidderUsername(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUsername");
        String userType = (String) session.getAttribute("loggedInUserType");

        if (username == null || userType == null || !"bidder".equalsIgnoreCase(userType)) {
            return null;
        }
        return username;
    }
    
    @RequestMapping(value = "/modify", method = RequestMethod.GET)
    public String loadModifyBidForm(@RequestParam("bidId") Integer bidId,
            Model model,
            HttpSession session) {

        String bidderUsername = getBidderUsername(session);
        if (bidderUsername == null) {
            return "redirect:/app/login";
        }

        org.fujitsu.training.codes.model.data.Bid existingBid =
                bidderAuctionDaoImpl.getBidderBid(bidId, bidderUsername);

        if (existingBid == null) {
            return "redirect:/app/bidder/auctions/my-bids";
        }

        Product product = bidderAuctionDaoImpl.getProductDetail(existingBid.getProductId());
        if (product == null) {
            return "redirect:/app/bidder/auctions/my-bids";
        }

        model.addAttribute("product", product);
        model.addAttribute("bidForm", bidderAuctionDaoImpl.toModifyForm(existingBid));
        model.addAttribute("existingBid", existingBid);
        model.addAttribute("remainingBidCount", bidderAuctionDaoImpl.getRemainingBidCount(bidderUsername));
        return "bidderModifyBidView";
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public String submitModifyBid(
            @Validated @ModelAttribute("bidForm") BidForm bidForm,
            BindingResult result,
            Model model,
            HttpSession session) {

        String bidderUsername = getBidderUsername(session);
        if (bidderUsername == null) {
            return "redirect:/app/login";
        }

        org.fujitsu.training.codes.model.data.Bid existingBid =
                bidderAuctionDaoImpl.getBidderBid(bidForm.getBidId(), bidderUsername);

        if (existingBid == null) {
            return "redirect:/app/bidder/auctions/my-bids";
        }

        Product product = bidderAuctionDaoImpl.getProductDetail(existingBid.getProductId());

        if (result.hasErrors()) {
            model.addAttribute("product", product);
            model.addAttribute("existingBid", existingBid);
            model.addAttribute("remainingBidCount", bidderAuctionDaoImpl.getRemainingBidCount(bidderUsername));
            return "bidderModifyBidView";
        }

        try {
            bidderAuctionDaoImpl.modifyBid(bidForm, bidderUsername);
            return "redirect:/app/bidder/auctions/my-bids";
        } catch (Exception ex) {
            logger.error("Modify bid failed for bidder {}: {}", bidderUsername, ex.getMessage(), ex);
            model.addAttribute("product", product);
            model.addAttribute("existingBid", existingBid);
            model.addAttribute("remainingBidCount", bidderAuctionDaoImpl.getRemainingBidCount(bidderUsername));
            model.addAttribute("bidError", ex.getMessage());
            return "bidderModifyBidView";
        }
    }

}*/

package org.fujitsu.training.codes.controller;

import java.math.BigDecimal;

import org.fujitsu.training.codes.helper.BidHelper;
import org.fujitsu.training.codes.helper.SessionRoleHelper;
import org.fujitsu.training.codes.model.form.BidForm;
import org.fujitsu.training.codes.validator.BidFormValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/bidder/auctions")
public class BidController {

    private static final String LOGIN_REDIRECT = "redirect:/app/login";
    private static final String LIST_VIEW = "bidderProductListView";
    private static final String MY_BIDS_VIEW = "bidderMyBidsView";

    private final BidHelper bidHelper;
    private final SessionRoleHelper sessionRoleHelper;
    private final BidFormValidator bidFormValidator;

    public BidController(BidHelper bidHelper,
            SessionRoleHelper sessionRoleHelper,
            BidFormValidator bidFormValidator) {
        this.bidHelper = bidHelper;
        this.sessionRoleHelper = sessionRoleHelper;
        this.bidFormValidator = bidFormValidator;
    }

    @InitBinder("bidForm")
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(bidFormValidator);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String loadOpenProducts(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "catId", required = false) Integer catId,
            @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
            Model model,
            HttpSession session) {

        String bidderUsername = sessionRoleHelper.getBidderUsername(session);
        if (bidderUsername == null) {
            return LOGIN_REDIRECT;
        }

        bidHelper.prepareOpenProducts(keyword, catId, minPrice, maxPrice, model);
        return LIST_VIEW;
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String loadProductDetail(
            @RequestParam("productId") Integer productId,
            @RequestParam(value = "success", required = false) String success,
            Model model,
            HttpSession session) {

        String bidderUsername = sessionRoleHelper.getBidderUsername(session);
        if (bidderUsername == null) {
            return LOGIN_REDIRECT;
        }

        return bidHelper.prepareProductDetail(productId, success, bidderUsername, model);
    }

    @RequestMapping(value = "/bid", method = RequestMethod.POST)
    public String submitBid(
            @Validated @ModelAttribute("bidForm") BidForm bidForm,
            BindingResult result,
            Model model,
            HttpSession session) {

        String bidderUsername = sessionRoleHelper.getBidderUsername(session);
        if (bidderUsername == null) {
            return LOGIN_REDIRECT;
        }

        return bidHelper.processSubmitBid(bidForm, result, model, bidderUsername);
    }

    @RequestMapping(value = "/my-bids", method = RequestMethod.GET)
    public String loadMyBids(Model model, HttpSession session) {
        String bidderUsername = sessionRoleHelper.getBidderUsername(session);
        if (bidderUsername == null) {
            return LOGIN_REDIRECT;
        }

        bidHelper.prepareMyBids(model, bidderUsername);
        return MY_BIDS_VIEW;
    }

    @RequestMapping(value = "/modify", method = RequestMethod.GET)
    public String loadModifyBidForm(@RequestParam("bidId") Integer bidId,
            Model model,
            HttpSession session) {

        String bidderUsername = sessionRoleHelper.getBidderUsername(session);
        if (bidderUsername == null) {
            return LOGIN_REDIRECT;
        }

        return bidHelper.prepareModifyBidForm(bidId, bidderUsername, model);
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public String submitModifyBid(
            @Validated @ModelAttribute("bidForm") BidForm bidForm,
            BindingResult result,
            Model model,
            HttpSession session) {

        String bidderUsername = sessionRoleHelper.getBidderUsername(session);
        if (bidderUsername == null) {
            return LOGIN_REDIRECT;
        }

        return bidHelper.processModifyBid(bidForm, result, model, bidderUsername);
    }
}
