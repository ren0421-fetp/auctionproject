package org.fujitsu.training.codes.dao.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.fujitsu.training.codes.model.data.Product;
import org.fujitsu.training.codes.model.form.ProductForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(locations = {
        "file:src/main/webapp/WEB-INF/app/context.xml",
        "file:src/main/webapp/WEB-INF/app/jdbc-context.xml"
})
@Transactional(transactionManager = "transactionManager")
@Rollback(value = true)
@WebAppConfiguration
public class TestSellerProductDaoImpl {

    @Autowired
    private SellerProductDaoImpl dao;
    
    @Test
    public void testSaveAndLoadSellerProduct() throws Exception {
        ProductForm form = new ProductForm();
        form.setProductName("Seller Test Product");
        form.setDescription("Testing seller product save");
        form.setCatId(1);
        form.setMinBidPrice(new BigDecimal("1000"));
        form.setStatus("open");
        form.setStartDate("2026-04-12T09:00");
        form.setEndDate("2026-04-19T09:00");
        form.setPhotoPath("/product/test-product.jpg");

        Integer productId = dao.saveProduct(form, "seller1");
        assertNotNull(productId);

        List<Product> products = dao.getSellerProducts("seller1");
        assertTrue(products.stream().anyMatch(p -> p.getProductId().equals(productId)));
    }
}
