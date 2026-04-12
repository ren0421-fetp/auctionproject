package org.fujitsu.training.codes.controller;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.AdminProductDaoImpl;
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
    private static final Logger logger = LogManager.getLogger(AdminProductController.class);

    private final AdminProductDaoImpl adminProductDaoImpl;
    private final ProductFormValidator productFormValidator;

    public AdminProductController(AdminProductDaoImpl adminProductDaoImpl,
            ProductFormValidator productFormValidator) {
        this.adminProductDaoImpl = adminProductDaoImpl;
        this.productFormValidator = productFormValidator;
    }

    @InitBinder("productForm")
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(productFormValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String loadProducts(
            @RequestParam(value = "productId", required = false) Integer productId,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "sellerUsername", required = false) String sellerUsername,
            @RequestParam(value = "success", required = false) String success,
            Model model,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/app/login";
        }

        ProductForm form = new ProductForm();
        if (productId != null) {
            ProductForm selected = adminProductDaoImpl.getProductForm(productId);
            if (selected != null) {
                form = selected;
            }
        }

        populatePage(model, form, status, sellerUsername);

        if ("1".equals(success)) {
            model.addAttribute("productActionSuccess", "Product saved successfully.");
        }

        return "adminProductView";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveProduct(
            @Validated @ModelAttribute("productForm") ProductForm form,
            BindingResult result,
            Model model,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/app/login";
        }

        if (form.getSellerUsername() == null || form.getSellerUsername().isBlank()) {
            result.rejectValue("sellerUsername", "seller.required", "Seller is required.");
        }

        if (result.hasErrors()) {
            populatePage(model, form, null, null);
            return "adminProductView";
        }

        try {
            handleProductPhoto(form);
            adminProductDaoImpl.saveProduct(form);
            return "redirect:/app/admin/products?success=1";
        } catch (Exception ex) {
            logger.error("Failed to save product {}: {}", form.getProductName(), ex.getMessage(), ex);
            populatePage(model, form, null, null);
            model.addAttribute("productActionError", ex.getMessage());
            return "adminProductView";
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteProduct(@RequestParam("productId") Integer productId,
            Model model,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/app/login";
        }

        try {
            adminProductDaoImpl.deleteProduct(productId);
            return "redirect:/app/admin/products?success=1";
        } catch (Exception ex) {
            logger.error("Failed to delete product {}: {}", productId, ex.getMessage(), ex);
            populatePage(model, new ProductForm(), null, null);
            model.addAttribute("productActionError", ex.getMessage());
            return "adminProductView";
        }
    }

    @RequestMapping(value = "/close", method = RequestMethod.POST)
    public String closeProduct(@RequestParam("productId") Integer productId,
            Model model,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/app/login";
        }

        try {
            adminProductDaoImpl.closeProduct(productId);
            return "redirect:/app/admin/products?success=1";
        } catch (Exception ex) {
            logger.error("Failed to close product {}: {}", productId, ex.getMessage(), ex);
            populatePage(model, new ProductForm(), null, null);
            model.addAttribute("productActionError", ex.getMessage());
            return "adminProductView";
        }
    }

    private void populatePage(Model model, ProductForm form, String status, String sellerUsername) {
        model.addAttribute("productForm", form);
        model.addAttribute("categoryOpts", adminProductDaoImpl.getCategories());
        model.addAttribute("sellerOpts", adminProductDaoImpl.getSellers());
        model.addAttribute("products", adminProductDaoImpl.getAllProducts(status, sellerUsername));
        model.addAttribute("selectedStatus", status);
        model.addAttribute("selectedSellerUsername", sellerUsername);
        model.addAttribute("editMode", form.getProductId() != null);
    }

    private void handleProductPhoto(ProductForm form) throws Exception {
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

        String fileName = "admin_" + System.currentTimeMillis() + "_" + safeFilename;
        File destination = new File(uploadDir, fileName);

        form.getPhotoFile().transferTo(destination);
        form.setPhotoPath("/app/product/" + fileName);
    }

    private boolean isAdmin(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUsername");
        String userType = (String) session.getAttribute("loggedInUserType");
        return username != null && userType != null && "admin".equalsIgnoreCase(userType);
    }
}
