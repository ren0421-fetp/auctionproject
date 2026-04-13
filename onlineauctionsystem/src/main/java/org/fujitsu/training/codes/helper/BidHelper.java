/*package org.fujitsu.training.codes.helper;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.BidDaoImpl;
import org.fujitsu.training.codes.model.data.Bid;
import org.fujitsu.training.codes.model.data.Product;
import org.fujitsu.training.codes.model.form.BidForm;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Component
public class BidHelper {
    private static final Logger logger = LogManager.getLogger(BidHelper.class);

    private static final String LIST_VIEW = "bidderProductListView";
    private static final String DETAIL_VIEW = "bidderProductDetailView";
    private static final String MY_BIDS_VIEW = "bidderMyBidsView";
    private static final String MODIFY_VIEW = "bidderModifyBidView";
    private static final String LIST_REDIRECT = "redirect:/app/bidder/auctions/list";
    private static final String MY_BIDS_REDIRECT = "redirect:/app/bidder/auctions/my-bids";

    private final BidDaoImpl bidDaoImpl;

    public BidHelper(BidDaoImpl bidDaoImpl) {
        this.bidDaoImpl = bidDaoImpl;
    }

    public void prepareOpenProducts(String keyword, Integer catId, BigDecimal minPrice,
            BigDecimal maxPrice, Model model) {
        model.addAttribute("openProducts",
                bidDaoImpl.getOpenProducts(keyword, catId, minPrice, maxPrice));
        model.addAttribute("categoryOpts", bidDaoImpl.getCategories());
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedCatId", catId);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
    }

    public String prepareProductDetail(Integer productId, String success,
            String bidderUsername, Model model) {
        Product product = bidDaoImpl.getProductDetail(productId);
        if (product == null) {
            return LIST_REDIRECT;
        }

        BidForm bidForm = new BidForm();
        bidForm.setProductId(productId);

        model.addAttribute("product", product);
        model.addAttribute("bidForm", bidForm);
        model.addAttribute("remainingBidCount", bidDaoImpl.getRemainingBidCount(bidderUsername));

        if ("1".equals(success)) {
            model.addAttribute("bidSuccess", "Bid placed successfully.");
        }

        return DETAIL_VIEW;
    }

    public String processSubmitBid(BidForm bidForm, BindingResult result,
            Model model, String bidderUsername) {
        Product product = bidDaoImpl.getProductDetail(bidForm.getProductId());
        if (product == null) {
            return LIST_REDIRECT;
        }

        if (result.hasErrors()) {
            populateDetailPage(model, product, bidForm, bidderUsername);
            return DETAIL_VIEW;
        }

        try {
            bidDaoImpl.placeBid(bidForm, bidderUsername);
            return "redirect:/app/bidder/auctions/detail?productId="
                    + bidForm.getProductId() + "&success=1";
        } catch (Exception ex) {
            logger.error("Bid submission failed for bidder {}: {}", bidderUsername, ex.getMessage(), ex);
            populateDetailPage(model, product, bidForm, bidderUsername);
            model.addAttribute("bidError", ex.getMessage());
            return DETAIL_VIEW;
        }
    }

    public void prepareMyBids(Model model, String bidderUsername) {
        model.addAttribute("myBids", bidDaoImpl.getBidderBids(bidderUsername));
    }

    public String prepareModifyBidForm(Integer bidId, String bidderUsername, Model model) {
        Bid existingBid = bidDaoImpl.getBidderBid(bidId, bidderUsername);
        if (existingBid == null) {
            return MY_BIDS_REDIRECT;
        }

        Product product = bidDaoImpl.getProductDetail(existingBid.getProductId());
        if (product == null) {
            return MY_BIDS_REDIRECT;
        }

        model.addAttribute("product", product);
        model.addAttribute("bidForm", bidDaoImpl.toModifyForm(existingBid));
        model.addAttribute("existingBid", existingBid);
        model.addAttribute("remainingBidCount", bidDaoImpl.getRemainingBidCount(bidderUsername));
        return MODIFY_VIEW;
    }

    public String processModifyBid(BidForm bidForm, BindingResult result,
            Model model, String bidderUsername) {
        Bid existingBid = bidDaoImpl.getBidderBid(bidForm.getBidId(), bidderUsername);
        if (existingBid == null) {
            return MY_BIDS_REDIRECT;
        }

        Product product = bidDaoImpl.getProductDetail(existingBid.getProductId());

        if (result.hasErrors()) {
            populateModifyPage(model, product, existingBid, bidForm, bidderUsername);
            return MODIFY_VIEW;
        }

        try {
            bidDaoImpl.modifyBid(bidForm, bidderUsername);
            return MY_BIDS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Modify bid failed for bidder {}: {}", bidderUsername, ex.getMessage(), ex);
            populateModifyPage(model, product, existingBid, bidForm, bidderUsername);
            model.addAttribute("bidError", ex.getMessage());
            return MODIFY_VIEW;
        }
    }

    private void populateDetailPage(Model model, Product product, BidForm bidForm,
            String bidderUsername) {
        model.addAttribute("product", product);
        model.addAttribute("bidForm", bidForm);
        model.addAttribute("remainingBidCount", bidDaoImpl.getRemainingBidCount(bidderUsername));
    }

    private void populateModifyPage(Model model, Product product, Bid existingBid,
            BidForm bidForm, String bidderUsername) {
        model.addAttribute("product", product);
        model.addAttribute("existingBid", existingBid);
        model.addAttribute("bidForm", bidForm);
        model.addAttribute("remainingBidCount", bidDaoImpl.getRemainingBidCount(bidderUsername));
    }
}
*/

package org.fujitsu.training.codes.helper;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.BidDaoImpl;
import org.fujitsu.training.codes.model.data.Bid;
import org.fujitsu.training.codes.model.data.Product;
import org.fujitsu.training.codes.model.form.BidForm;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Component
public class BidHelper {
    private static final Logger logger = LogManager.getLogger("bidder-flow");

    private static final String LIST_VIEW = "bidderProductListView";
    private static final String DETAIL_VIEW = "bidderProductDetailView";
    private static final String MY_BIDS_VIEW = "bidderMyBidsView";
    private static final String MODIFY_VIEW = "bidderModifyBidView";
    private static final String LIST_REDIRECT = "redirect:/app/bidder/auctions/list";
    private static final String MY_BIDS_REDIRECT = "redirect:/app/bidder/auctions/my-bids";

    private final BidDaoImpl bidDaoImpl;

    public BidHelper(BidDaoImpl bidDaoImpl) {
        this.bidDaoImpl = bidDaoImpl;
    }

    public void prepareOpenProducts(String keyword, Integer catId, BigDecimal minPrice,
            BigDecimal maxPrice, Model model) {
        logger.info("Loading bidder auction list. keyword={}, catId={}, minPrice={}, maxPrice={}",
                keyword, catId, minPrice, maxPrice);

        model.addAttribute("openProducts",
                bidDaoImpl.getOpenProducts(keyword, catId, minPrice, maxPrice));
        model.addAttribute("categoryOpts", bidDaoImpl.getCategories());
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedCatId", catId);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);

        logger.info("Bidder auction list loaded.");
    }

    public String prepareProductDetail(Integer productId, String success,
            String bidderUsername, Model model) {
        logger.info("Loading bidder product detail. bidderUsername={}, productId={}",
                bidderUsername, productId);

        Product product = bidDaoImpl.getProductDetail(productId);
        if (product == null) {
            logger.warn("Bidder product detail load failed. productId={} not found.", productId);
            return LIST_REDIRECT;
        }

        BidForm bidForm = new BidForm();
        bidForm.setProductId(productId);

        model.addAttribute("product", product);
        model.addAttribute("bidForm", bidForm);
        model.addAttribute("remainingBidCount", bidDaoImpl.getRemainingBidCount(bidderUsername));

        if ("1".equals(success)) {
            model.addAttribute("bidSuccess", "Bid placed successfully.");
        }

        logger.info("Bidder product detail loaded. bidderUsername={}, productId={}",
                bidderUsername, productId);
        return DETAIL_VIEW;
    }

    public String processSubmitBid(BidForm bidForm, BindingResult result,
            Model model, String bidderUsername) {
        logger.info("Processing bid submission. bidderUsername={}, productId={}",
                bidderUsername, bidForm.getProductId());

        Product product = bidDaoImpl.getProductDetail(bidForm.getProductId());
        if (product == null) {
            logger.warn("Bid submission failed because productId={} was not found.",
                    bidForm.getProductId());
            return LIST_REDIRECT;
        }

        if (result.hasErrors()) {
            populateDetailPage(model, product, bidForm, bidderUsername);
            logger.warn("Bid validation failed. bidderUsername={}, productId={}",
                    bidderUsername, bidForm.getProductId());
            return DETAIL_VIEW;
        }

        try {
            bidDaoImpl.placeBid(bidForm, bidderUsername);
            logger.info("Bid submission completed. bidderUsername={}, productId={}",
                    bidderUsername, bidForm.getProductId());
            return "redirect:/app/bidder/auctions/detail?productId="
                    + bidForm.getProductId() + "&success=1";
        } catch (Exception ex) {
            logger.error("Bid submission failed for bidder {}: {}", bidderUsername, ex.getMessage(), ex);
            populateDetailPage(model, product, bidForm, bidderUsername);
            model.addAttribute("bidError", ex.getMessage());
            return DETAIL_VIEW;
        }
    }

    public void prepareMyBids(Model model, String bidderUsername) {
        logger.info("Loading bidder my-bids page. bidderUsername={}", bidderUsername);
        model.addAttribute("myBids", bidDaoImpl.getBidderBids(bidderUsername));
        logger.info("Bidder my-bids page loaded. bidderUsername={}", bidderUsername);
    }

    public String prepareModifyBidForm(Integer bidId, String bidderUsername, Model model) {
        logger.info("Loading modify-bid form. bidderUsername={}, bidId={}", bidderUsername, bidId);

        Bid existingBid = bidDaoImpl.getBidderBid(bidId, bidderUsername);
        if (existingBid == null) {
            logger.warn("Modify-bid form load failed. bidId={} not found for bidder={}",
                    bidId, bidderUsername);
            return MY_BIDS_REDIRECT;
        }

        Product product = bidDaoImpl.getProductDetail(existingBid.getProductId());
        if (product == null) {
            logger.warn("Modify-bid form load failed. productId={} not found for bidId={}",
                    existingBid.getProductId(), bidId);
            return MY_BIDS_REDIRECT;
        }

        model.addAttribute("product", product);
        model.addAttribute("bidForm", bidDaoImpl.toModifyForm(existingBid));
        model.addAttribute("existingBid", existingBid);
        model.addAttribute("remainingBidCount", bidDaoImpl.getRemainingBidCount(bidderUsername));

        logger.info("Modify-bid form loaded. bidderUsername={}, bidId={}", bidderUsername, bidId);
        return MODIFY_VIEW;
    }

    public String processModifyBid(BidForm bidForm, BindingResult result,
            Model model, String bidderUsername) {
        logger.info("Processing bid modification. bidderUsername={}, bidId={}",
                bidderUsername, bidForm.getBidId());

        Bid existingBid = bidDaoImpl.getBidderBid(bidForm.getBidId(), bidderUsername);
        if (existingBid == null) {
            logger.warn("Bid modification failed. bidId={} not found for bidder={}",
                    bidForm.getBidId(), bidderUsername);
            return MY_BIDS_REDIRECT;
        }

        Product product = bidDaoImpl.getProductDetail(existingBid.getProductId());

        if (result.hasErrors()) {
            populateModifyPage(model, product, existingBid, bidForm, bidderUsername);
            logger.warn("Bid modification validation failed. bidderUsername={}, bidId={}",
                    bidderUsername, bidForm.getBidId());
            return MODIFY_VIEW;
        }

        try {
            bidDaoImpl.modifyBid(bidForm, bidderUsername);
            logger.info("Bid modification completed. bidderUsername={}, bidId={}",
                    bidderUsername, bidForm.getBidId());
            return MY_BIDS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Modify bid failed for bidder {}: {}", bidderUsername, ex.getMessage(), ex);
            populateModifyPage(model, product, existingBid, bidForm, bidderUsername);
            model.addAttribute("bidError", ex.getMessage());
            return MODIFY_VIEW;
        }
    }

    private void populateDetailPage(Model model, Product product, BidForm bidForm,
            String bidderUsername) {
        model.addAttribute("product", product);
        model.addAttribute("bidForm", bidForm);
        model.addAttribute("remainingBidCount", bidDaoImpl.getRemainingBidCount(bidderUsername));
    }

    private void populateModifyPage(Model model, Product product, Bid existingBid,
            BidForm bidForm, String bidderUsername) {
        model.addAttribute("product", product);
        model.addAttribute("existingBid", existingBid);
        model.addAttribute("bidForm", bidForm);
        model.addAttribute("remainingBidCount", bidDaoImpl.getRemainingBidCount(bidderUsername));
    }
}
