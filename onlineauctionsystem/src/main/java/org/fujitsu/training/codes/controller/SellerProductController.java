package org.fujitsu.training.codes.controller;

import org.fujitsu.training.codes.helper.SellerProductHelper;
import org.fujitsu.training.codes.model.form.ProductForm;
import org.fujitsu.training.codes.validator.ProductFormValidator;
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
@RequestMapping("/seller/product")
public class SellerProductController {

	private static final String LOGIN_REDIRECT = "redirect:/app/login";
	private static final String FORM_VIEW = "sellerProductFormView";
	private static final String LIST_VIEW = "sellerProductListView";
	private static final String BID_LIST_VIEW = "sellerBidListView";

	private final SellerProductHelper sellerProductHelper;
	private final ProductFormValidator productFormValidator;

	public SellerProductController(SellerProductHelper sellerProductHelper, ProductFormValidator productFormValidator) {
		this.sellerProductHelper = sellerProductHelper;
		this.productFormValidator = productFormValidator;
	}

	@InitBinder("productForm")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(productFormValidator);
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String loadAddForm(Model model, HttpSession session) {
		String sellerUsername = getSellerUsername(session);
		if (sellerUsername == null) {
			return LOGIN_REDIRECT;
		}

		sellerProductHelper.prepareAddForm(model);
		return FORM_VIEW;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveProduct(@Validated @ModelAttribute("productForm") ProductForm form, BindingResult result,
			Model model, HttpSession session) {

		String sellerUsername = getSellerUsername(session);
		if (sellerUsername == null) {
			return LOGIN_REDIRECT;
		}

		return sellerProductHelper.processSaveProduct(form, result, model, sellerUsername);
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String loadProductList(Model model, HttpSession session) {
		String sellerUsername = getSellerUsername(session);
		if (sellerUsername == null) {
			return LOGIN_REDIRECT;
		}

		sellerProductHelper.prepareProductList(model, sellerUsername);
		return LIST_VIEW;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String loadEditForm(@RequestParam("productId") Integer productId, Model model, HttpSession session) {

		String sellerUsername = getSellerUsername(session);
		if (sellerUsername == null) {
			return LOGIN_REDIRECT;
		}

		return sellerProductHelper.prepareEditForm(productId, sellerUsername, model);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteProduct(@RequestParam("productId") Integer productId, Model model, HttpSession session) {

		String sellerUsername = getSellerUsername(session);
		if (sellerUsername == null) {
			return LOGIN_REDIRECT;
		}

		return sellerProductHelper.processDeleteProduct(productId, model, sellerUsername);
	}

	@RequestMapping(value = "/bids", method = RequestMethod.GET)
	public String loadSellerBids(Model model, HttpSession session) {
		String sellerUsername = getSellerUsername(session);
		if (sellerUsername == null) {
			return LOGIN_REDIRECT;
		}

		sellerProductHelper.prepareSellerBids(model, sellerUsername);
		return BID_LIST_VIEW;
	}

	private String getSellerUsername(HttpSession session) {
		String username = (String) session.getAttribute("loggedInUsername");
		String userType = (String) session.getAttribute("loggedInUserType");

		if (username == null || userType == null || !"seller".equalsIgnoreCase(userType)) {
			return null;
		}

		return username;
	}
}
