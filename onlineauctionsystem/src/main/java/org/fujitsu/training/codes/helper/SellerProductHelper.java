/*package org.fujitsu.training.codes.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.SellerProductDaoImpl;
import org.fujitsu.training.codes.model.data.Product;
import org.fujitsu.training.codes.model.form.ProductForm;
import org.fujitsu.training.codes.service.FileService;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Component
public class SellerProductHelper {
    private static final Logger logger = LogManager.getLogger(SellerProductHelper.class);

    private static final String FORM_VIEW = "sellerProductFormView";
    private static final String LIST_VIEW = "sellerProductListView";
    private static final String LIST_REDIRECT = "redirect:/app/seller/product/list";

    private final SellerProductDaoImpl sellerProductDaoImpl;
    private final FileService fileService;

    public SellerProductHelper(SellerProductDaoImpl sellerProductDaoImpl,
            FileService fileService) {
        this.sellerProductDaoImpl = sellerProductDaoImpl;
        this.fileService = fileService;
    }

    public void prepareAddForm(Model model) {
        populateFormPage(model, new ProductForm());
    }

    public String prepareEditForm(Integer productId, String sellerUsername, Model model) {
        Product product = sellerProductDaoImpl.getSellerProduct(productId, sellerUsername);
        if (product == null) {
            return LIST_REDIRECT;
        }

        populateFormPage(model, sellerProductDaoImpl.toForm(product));
        return FORM_VIEW;
    }

    public void prepareProductList(Model model, String sellerUsername) {
        model.addAttribute("sellerProducts", sellerProductDaoImpl.getSellerProducts(sellerUsername));
    }

    public void prepareSellerBids(Model model, String sellerUsername) {
        model.addAttribute("sellerBids", sellerProductDaoImpl.getSellerBids(sellerUsername));
    }

    public String processSaveProduct(ProductForm form,
            BindingResult result,
            Model model,
            String sellerUsername) {

        if (result.hasErrors()) {
            populateFormPage(model, form);
            return FORM_VIEW;
        }

        try {
            handleProductPhoto(form, sellerUsername);
            Integer productId = sellerProductDaoImpl.saveProduct(form, sellerUsername);
            logger.info("Seller {} saved product {}", sellerUsername, productId);
            return LIST_REDIRECT;
        } catch (Exception ex) {
            logger.error("Failed to save product for seller {}: {}", sellerUsername, ex.getMessage(), ex);
            populateFormPage(model, form);
            model.addAttribute("saveError", ex.getMessage());
            return FORM_VIEW;
        }
    }

    public String processDeleteProduct(Integer productId, Model model, String sellerUsername) {
        try {
            sellerProductDaoImpl.deleteSellerProduct(productId, sellerUsername);
            return LIST_REDIRECT;
        } catch (Exception ex) {
            logger.error("Failed to delete product {} for seller {}: {}", productId, sellerUsername, ex.getMessage(), ex);
            prepareProductList(model, sellerUsername);
            model.addAttribute("deleteError", ex.getMessage());
            return LIST_VIEW;
        }
    }

    private void populateFormPage(Model model, ProductForm form) {
        model.addAttribute("productForm", form);
        model.addAttribute("categoryOpts", sellerProductDaoImpl.getCategories());
        model.addAttribute("editMode", form.getProductId() != null);
    }

    private void handleProductPhoto(ProductForm form, String sellerUsername) throws Exception {
        if (form.getPhotoFile() == null || form.getPhotoFile().isEmpty()) {
            form.setPhotoPath(form.getCurrentPhotoPath());
            return;
        }

        String photoPath = fileService.saveFile(form.getPhotoFile(), "product", sellerUsername);
        form.setPhotoPath(photoPath);
    }
}
*/

package org.fujitsu.training.codes.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.SellerProductDaoImpl;
import org.fujitsu.training.codes.model.data.Product;
import org.fujitsu.training.codes.model.form.ProductForm;
import org.fujitsu.training.codes.service.FileService;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Component
public class SellerProductHelper {
    private static final Logger logger = LogManager.getLogger("seller-flow");

    private static final String FORM_VIEW = "sellerProductFormView";
    private static final String LIST_VIEW = "sellerProductListView";
    private static final String LIST_REDIRECT = "redirect:/app/seller/product/list";

    private final SellerProductDaoImpl sellerProductDaoImpl;
    private final FileService fileService;

    public SellerProductHelper(SellerProductDaoImpl sellerProductDaoImpl,
            FileService fileService) {
        this.sellerProductDaoImpl = sellerProductDaoImpl;
        this.fileService = fileService;
    }

    public void prepareAddForm(Model model) {
        logger.info("Loading seller add-product form.");
        populateFormPage(model, new ProductForm());
        logger.info("Seller add-product form loaded.");
    }

    public String prepareEditForm(Integer productId, String sellerUsername, Model model) {
        logger.info("Loading seller edit-product form for seller={} productId={}.", sellerUsername, productId);
        Product product = sellerProductDaoImpl.getSellerProduct(productId, sellerUsername);
        if (product == null) {
            logger.warn("Seller edit-product load failed. productId={} not found for seller={}.", productId, sellerUsername);
            return LIST_REDIRECT;
        }

        populateFormPage(model, sellerProductDaoImpl.toForm(product));
        logger.info("Seller edit-product form loaded for seller={} productId={}.", sellerUsername, productId);
        return FORM_VIEW;
    }

    public void prepareProductList(Model model, String sellerUsername) {
        logger.info("Loading seller product list for {}.", sellerUsername);
        model.addAttribute("sellerProducts", sellerProductDaoImpl.getSellerProducts(sellerUsername));
        logger.info("Seller product list loaded for {}.", sellerUsername);
    }

    public void prepareSellerBids(Model model, String sellerUsername) {
        logger.info("Loading seller bid list for {}.", sellerUsername);
        model.addAttribute("sellerBids", sellerProductDaoImpl.getSellerBids(sellerUsername));
        logger.info("Seller bid list loaded for {}.", sellerUsername);
    }

    public String processSaveProduct(ProductForm form, BindingResult result, Model model, String sellerUsername) {
        logger.info("Processing seller product save for seller={} productId={}.", sellerUsername, form.getProductId());

        if (result.hasErrors()) {
            populateFormPage(model, form);
            logger.warn("Seller product validation failed for seller={} productId={}.", sellerUsername, form.getProductId());
            return FORM_VIEW;
        }

        try {
            handleProductPhoto(form, sellerUsername);
            Integer productId = sellerProductDaoImpl.saveProduct(form, sellerUsername);
            logger.info("Seller product save completed for seller={} productId={}.", sellerUsername, productId);
            return LIST_REDIRECT;
        } catch (Exception ex) {
            logger.error("Failed to save product for seller {}: {}", sellerUsername, ex.getMessage(), ex);
            populateFormPage(model, form);
            model.addAttribute("saveError", ex.getMessage());
            return FORM_VIEW;
        }
    }

    public String processDeleteProduct(Integer productId, Model model, String sellerUsername) {
        logger.info("Processing seller product delete for seller={} productId={}.", sellerUsername, productId);
        try {
            sellerProductDaoImpl.deleteSellerProduct(productId, sellerUsername);
            logger.info("Seller product delete completed for seller={} productId={}.", sellerUsername, productId);
            return LIST_REDIRECT;
        } catch (Exception ex) {
            logger.error("Failed to delete product {} for seller {}: {}", productId, sellerUsername, ex.getMessage(), ex);
            prepareProductList(model, sellerUsername);
            model.addAttribute("deleteError", ex.getMessage());
            return LIST_VIEW;
        }
    }

    private void populateFormPage(Model model, ProductForm form) {
        model.addAttribute("productForm", form);
        model.addAttribute("categoryOpts", sellerProductDaoImpl.getCategories());
        model.addAttribute("editMode", form.getProductId() != null);
    }

    private void handleProductPhoto(ProductForm form, String sellerUsername) throws Exception {
        if (form.getPhotoFile() == null || form.getPhotoFile().isEmpty()) {
            form.setPhotoPath(form.getCurrentPhotoPath());
            return;
        }

        String photoPath = fileService.saveFile(form.getPhotoFile(), "product", sellerUsername);
        form.setPhotoPath(photoPath);
    }
}
