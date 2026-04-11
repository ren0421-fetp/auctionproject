package org.fujitsu.training.codes.controller;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.SellerProductDaoImpl;
import org.fujitsu.training.codes.model.data.Product;
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
    private static final Logger logger = LogManager.getLogger(SellerProductController.class);

    private final SellerProductDaoImpl sellerProductDaoImpl;
    private final ProductFormValidator productFormValidator;

    public SellerProductController(SellerProductDaoImpl sellerProductDaoImpl,
            ProductFormValidator productFormValidator) {
        this.sellerProductDaoImpl = sellerProductDaoImpl;
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
            return "loginView";
        }

        model.addAttribute("productForm", new ProductForm());
        model.addAttribute("categoryOpts", sellerProductDaoImpl.getCategories());
        model.addAttribute("editMode", false);
        return "sellerProductFormView";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveProduct(
            @Validated @ModelAttribute("productForm") ProductForm form,
            BindingResult result,
            Model model,
            HttpSession session) {

        String sellerUsername = getSellerUsername(session);
        if (sellerUsername == null) {
            return "loginView";
        }

        if (result.hasErrors()) {
            model.addAttribute("categoryOpts", sellerProductDaoImpl.getCategories());
            model.addAttribute("editMode", form.getProductId() != null);
            return "sellerProductFormView";
        }

        try {
            handleProductPhoto(form, sellerUsername);
            Integer productId = sellerProductDaoImpl.saveProduct(form, sellerUsername);
            logger.info("Seller {} saved product {}", sellerUsername, productId);
            return "sellerProductListView";
        } catch (Exception ex) {
            logger.error("Failed to save product for seller {}: {}", sellerUsername, ex.getMessage(), ex);
            model.addAttribute("categoryOpts", sellerProductDaoImpl.getCategories());
            model.addAttribute("editMode", form.getProductId() != null);
            model.addAttribute("saveError", ex.getMessage());
            return "sellerProductFormView";
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String loadProductList(Model model, HttpSession session) {
        String sellerUsername = getSellerUsername(session);
        if (sellerUsername == null) {
            return "loginView";
        }

        model.addAttribute("sellerProducts", sellerProductDaoImpl.getSellerProducts(sellerUsername));
        return "sellerProductListView";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String loadEditForm(@RequestParam("productId") Integer productId,
            Model model,
            HttpSession session) {
        String sellerUsername = getSellerUsername(session);
        if (sellerUsername == null) {
            return "redirect:/login";
        }

        Product product = sellerProductDaoImpl.getSellerProduct(productId, sellerUsername);
        if (product == null) {
            return "redirect:/seller/product/list";
        }

        model.addAttribute("productForm", sellerProductDaoImpl.toForm(product));
        model.addAttribute("categoryOpts", sellerProductDaoImpl.getCategories());
        model.addAttribute("editMode", true);
        return "sellerProductFormView";
    }

    private String getSellerUsername(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUsername");
        String userType = (String) session.getAttribute("loggedInUserType");

        if (username == null || userType == null || !"seller".equalsIgnoreCase(userType)) {
            return null;
        }
        return username;
    }

    private void handleProductPhoto(ProductForm form, String sellerUsername) throws Exception {
        if (form.getPhotoFile() == null || form.getPhotoFile().isEmpty()) {
            form.setPhotoPath(form.getCurrentPhotoPath());
            return;
        }

        String uploadDirPath = "C:/auction_uploads/product/";
        File uploadDir = new File(uploadDirPath);

        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            throw new IllegalStateException("Failed to create product upload directory.");
        }

        String originalFilename = form.getPhotoFile().getOriginalFilename();
        String safeFilename = (originalFilename == null ? "product.jpg" : originalFilename)
                .replaceAll("[^a-zA-Z0-9._-]", "_");

        String fileName = sellerUsername + "_" + System.currentTimeMillis() + "_" + safeFilename;
        File destination = new File(uploadDir, fileName);

        form.getPhotoFile().transferTo(destination);
        form.setPhotoPath("/app/product/" + fileName);
    }
}
