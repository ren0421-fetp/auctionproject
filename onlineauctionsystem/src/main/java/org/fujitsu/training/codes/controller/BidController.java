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

	public BidController(BidHelper bidHelper, SessionRoleHelper sessionRoleHelper, BidFormValidator bidFormValidator) {
		this.bidHelper = bidHelper;
		this.sessionRoleHelper = sessionRoleHelper;
		this.bidFormValidator = bidFormValidator;
	}

	@InitBinder("bidForm")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(bidFormValidator);
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String loadOpenProducts(@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "catId", required = false) Integer catId,
			@RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
			@RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice, Model model, HttpSession session) {

		String bidderUsername = sessionRoleHelper.getBidderUsername(session);
		if (bidderUsername == null) {
			return LOGIN_REDIRECT;
		}

		bidHelper.prepareOpenProducts(keyword, catId, minPrice, maxPrice, model);
		return LIST_VIEW;
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String loadProductDetail(@RequestParam("productId") Integer productId,
			@RequestParam(value = "success", required = false) String success, Model model, HttpSession session) {

		String bidderUsername = sessionRoleHelper.getBidderUsername(session);
		if (bidderUsername == null) {
			return LOGIN_REDIRECT;
		}

		return bidHelper.prepareProductDetail(productId, success, bidderUsername, model);
	}

	@RequestMapping(value = "/bid", method = RequestMethod.POST)
	public String submitBid(@Validated @ModelAttribute("bidForm") BidForm bidForm, BindingResult result, Model model,
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
	public String loadModifyBidForm(@RequestParam("bidId") Integer bidId, Model model, HttpSession session) {

		String bidderUsername = sessionRoleHelper.getBidderUsername(session);
		if (bidderUsername == null) {
			return LOGIN_REDIRECT;
		}

		return bidHelper.prepareModifyBidForm(bidId, bidderUsername, model);
	}

	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String submitModifyBid(@Validated @ModelAttribute("bidForm") BidForm bidForm, BindingResult result,
			Model model, HttpSession session) {

		String bidderUsername = sessionRoleHelper.getBidderUsername(session);
		if (bidderUsername == null) {
			return LOGIN_REDIRECT;
		}

		return bidHelper.processModifyBid(bidForm, result, model, bidderUsername);
	}
}
