package org.fujitsu.training.codes.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.fujitsu.training.codes.model.data.Product;
import org.fujitsu.training.codes.model.form.BidForm;
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
public class TestBidDaoImpl {

    @Autowired
    private BidDaoImpl dao;

    @Test
    public void testGetOpenProductsWithAmountFilter() {
        List<Product> products = dao.getOpenProducts("Sap", null,
                new BigDecimal("1000"), new BigDecimal("60000"));
        assertNotNull(products);
    }



    @Test
    public void testGetRemainingBidCount() {
        Integer remaining = dao.getRemainingBidCount("bidder1");
        assertNotNull(remaining);
        assertTrue(remaining > 0);
    }

    @Test
    public void testPlaceBid() throws Exception {
        Product product = dao.getProductDetail(1);
        assertNotNull(product);

        BigDecimal base = product.getMinBidPrice();
        if (product.getCurrentHighestBid() != null && product.getCurrentHighestBid().compareTo(base) > 0) {
            base = product.getCurrentHighestBid();
        }

        Integer before = dao.getRemainingBidCount("bidder1");

        BidForm form = new BidForm();
        form.setProductId(1);
        form.setBidPrice(base.add(new BigDecimal("1000")));

        dao.placeBid(form, "bidder1");

        Integer after = dao.getRemainingBidCount("bidder1");
        assertEquals(before - 1, after);
    }
    
    @Test
    public void testGetBidderBids() {
        List<org.fujitsu.training.codes.model.data.Bid> bids = dao.getBidderBids("bidder1");
        assertNotNull(bids);
    }
    
    @Test
    public void testGetBidderBid() {
        org.fujitsu.training.codes.model.data.Bid bid = dao.getBidderBid(1, "bidder1");
        assertNotNull(bid);
    }

    @Test
    public void testModifyBid() throws Exception {
        Integer before = dao.getRemainingBidCount("bidder1");

        BidForm form = new BidForm();
        form.setBidId(1);
        form.setProductId(1);
        form.setBidPrice(new BigDecimal("560000"));

        dao.modifyBid(form, "bidder1");

        Integer after = dao.getRemainingBidCount("bidder1");
        assertEquals(before - 1, after);
    }


}
