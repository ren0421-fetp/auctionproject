package org.fujitsu.training.codes.controller;

import org.fujitsu.training.codes.helper.AdminProductHelper;
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
@RequestMapping("/admin/products")
public class AdminProductController {

	private static final String LOGIN_REDIRECT = "redirect:/app/login";

	private final AdminProductHelper adminProductHelper;
	private final ProductFormValidator productFormValidator;

	public AdminProductController(AdminProductHelper adminProductHelper, ProductFormValidator productFormValidator) {
		this.adminProductHelper = adminProductHelper;
		this.productFormValidator = productFormValidator;
	}

	@InitBinder("productForm")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(productFormValidator);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String loadProducts(@RequestParam(value = "productId", required = false) Integer productId,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "sellerUsername", required = false) String sellerUsername,
			@RequestParam(value = "success", required = false) String success, Model model, HttpSession session) {

		if (!isAdmin(session)) {
			return LOGIN_REDIRECT;
		}

		adminProductHelper.prepareLoadProducts(productId, status, sellerUsername, success, model);
		return "adminProductView";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveProduct(@Validated @ModelAttribute("productForm") ProductForm form, BindingResult result,
			Model model, HttpSession session) {

		if (!isAdmin(session)) {
			return LOGIN_REDIRECT;
		}

		return adminProductHelper.processSaveProduct(form, result, model);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteProduct(@RequestParam("productId") Integer productId, Model model, HttpSession session) {

		if (!isAdmin(session)) {
			return LOGIN_REDIRECT;
		}

		return adminProductHelper.processDeleteProduct(productId, model);
	}

	@RequestMapping(value = "/close", method = RequestMethod.POST)
	public String closeProduct(@RequestParam("productId") Integer productId, Model model, HttpSession session) {

		if (!isAdmin(session)) {
			return LOGIN_REDIRECT;
		}

		return adminProductHelper.processCloseProduct(productId, model);
	}

	private boolean isAdmin(HttpSession session) {
		String username = (String) session.getAttribute("loggedInUsername");
		String userType = (String) session.getAttribute("loggedInUserType");
		return username != null && userType != null && "admin".equalsIgnoreCase(userType);
	}
}
