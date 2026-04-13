/*package org.fujitsu.training.codes.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.AdminProductDaoImpl;
import org.fujitsu.training.codes.model.form.ProductForm;
import org.fujitsu.training.codes.service.FileService;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Component
public class AdminProductHelper {
    private static final Logger logger = LogManager.getLogger(AdminProductHelper.class);

    private static final String VIEW_NAME = "adminProductView";
    private static final String SUCCESS_REDIRECT = "redirect:/app/admin/products?success=1";

    private final AdminProductDaoImpl adminProductDaoImpl;
    private final FileService fileService;

    public AdminProductHelper(AdminProductDaoImpl adminProductDaoImpl,
            FileService fileService) {
        this.adminProductDaoImpl = adminProductDaoImpl;
        this.fileService = fileService;
    }

    public void prepareLoadProducts(Integer productId, String status, String sellerUsername,
            String success, Model model) {
        ProductForm form = resolveProductForm(productId);
        populatePage(model, form, status, sellerUsername);

        if ("1".equals(success)) {
            model.addAttribute("productActionSuccess", "Product saved successfully.");
        }
    }

    public String processSaveProduct(ProductForm form, BindingResult result, Model model) {
        validateSellerSelection(form, result);

        if (result.hasErrors()) {
            populatePage(model, form, null, null);
            return VIEW_NAME;
        }

        try {
            handleProductPhoto(form);
            adminProductDaoImpl.saveProduct(form);
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Failed to save product {}: {}", form.getProductName(), ex.getMessage(), ex);
            populatePage(model, form, null, null);
            model.addAttribute("productActionError", ex.getMessage());
            return VIEW_NAME;
        }
    }

    public String processDeleteProduct(Integer productId, Model model) {
        try {
            adminProductDaoImpl.deleteProduct(productId);
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Failed to delete product {}: {}", productId, ex.getMessage(), ex);
            populatePage(model, new ProductForm(), null, null);
            model.addAttribute("productActionError", ex.getMessage());
            return VIEW_NAME;
        }
    }

    public String processCloseProduct(Integer productId, Model model) {
        try {
            adminProductDaoImpl.closeProduct(productId);
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Failed to close product {}: {}", productId, ex.getMessage(), ex);
            populatePage(model, new ProductForm(), null, null);
            model.addAttribute("productActionError", ex.getMessage());
            return VIEW_NAME;
        }
    }

    private ProductForm resolveProductForm(Integer productId) {
        if (productId == null) {
            return new ProductForm();
        }

        ProductForm selected = adminProductDaoImpl.getProductForm(productId);
        return selected == null ? new ProductForm() : selected;
    }

    private void validateSellerSelection(ProductForm form, BindingResult result) {
        if (form.getSellerUsername() == null || form.getSellerUsername().isBlank()) {
            result.rejectValue("sellerUsername", "seller.required", "Seller is required.");
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

        String photoPath = fileService.saveFile(form.getPhotoFile(), "product", "admin");
        form.setPhotoPath(photoPath);
    }
}*/

package org.fujitsu.training.codes.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.AdminProductDaoImpl;
import org.fujitsu.training.codes.model.form.ProductForm;
import org.fujitsu.training.codes.service.FileService;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Component
public class AdminProductHelper {
    private static final Logger logger = LogManager.getLogger("admin-flow");

    private static final String VIEW_NAME = "adminProductView";
    private static final String SUCCESS_REDIRECT = "redirect:/app/admin/products?success=1";

    private final AdminProductDaoImpl adminProductDaoImpl;
    private final FileService fileService;

    public AdminProductHelper(AdminProductDaoImpl adminProductDaoImpl,
            FileService fileService) {
        this.adminProductDaoImpl = adminProductDaoImpl;
        this.fileService = fileService;
    }

    public void prepareLoadProducts(Integer productId, String status, String sellerUsername,
            String success, Model model) {
        logger.info("Loading admin product page. productId={}, status={}, sellerUsername={}",
                productId, status, sellerUsername);

        ProductForm form = resolveProductForm(productId);
        populatePage(model, form, status, sellerUsername);

        if ("1".equals(success)) {
            model.addAttribute("productActionSuccess", "Product saved successfully.");
        }

        logger.info("Admin product page loaded. productId={}", productId);
    }

    public String processSaveProduct(ProductForm form, BindingResult result, Model model) {
        logger.info("Processing admin product save. productId={}, productName={}",
                form.getProductId(), form.getProductName());

        validateSellerSelection(form, result);

        if (result.hasErrors()) {
            populatePage(model, form, null, null);
            logger.warn("Admin product validation failed. productId={}, productName={}",
                    form.getProductId(), form.getProductName());
            return VIEW_NAME;
        }

        try {
            handleProductPhoto(form);
            adminProductDaoImpl.saveProduct(form);
            logger.info("Admin product save completed. productId={}, productName={}",
                    form.getProductId(), form.getProductName());
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Failed to save product {}: {}", form.getProductName(), ex.getMessage(), ex);
            populatePage(model, form, null, null);
            model.addAttribute("productActionError", ex.getMessage());
            return VIEW_NAME;
        }
    }

    public String processDeleteProduct(Integer productId, Model model) {
        logger.info("Processing admin product delete. productId={}", productId);
        try {
            adminProductDaoImpl.deleteProduct(productId);
            logger.info("Admin product delete completed. productId={}", productId);
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Failed to delete product {}: {}", productId, ex.getMessage(), ex);
            populatePage(model, new ProductForm(), null, null);
            model.addAttribute("productActionError", ex.getMessage());
            return VIEW_NAME;
        }
    }

    public String processCloseProduct(Integer productId, Model model) {
        logger.info("Processing admin product close. productId={}", productId);
        try {
            adminProductDaoImpl.closeProduct(productId);
            logger.info("Admin product close completed. productId={}", productId);
            return SUCCESS_REDIRECT;
        } catch (Exception ex) {
            logger.error("Failed to close product {}: {}", productId, ex.getMessage(), ex);
            populatePage(model, new ProductForm(), null, null);
            model.addAttribute("productActionError", ex.getMessage());
            return VIEW_NAME;
        }
    }

    private ProductForm resolveProductForm(Integer productId) {
        if (productId == null) {
            return new ProductForm();
        }

        ProductForm selected = adminProductDaoImpl.getProductForm(productId);
        return selected == null ? new ProductForm() : selected;
    }

    private void validateSellerSelection(ProductForm form, BindingResult result) {
        if (form.getSellerUsername() == null || form.getSellerUsername().isBlank()) {
            result.rejectValue("sellerUsername", "seller.required", "Seller is required.");
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

        String photoPath = fileService.saveFile(form.getPhotoFile(), "product", "admin");
        form.setPhotoPath(photoPath);
    }
}

