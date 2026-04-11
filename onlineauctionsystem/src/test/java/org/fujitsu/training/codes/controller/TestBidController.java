package org.fujitsu.training.codes.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {
        "file:src/main/webapp/WEB-INF/app/context.xml",
        "file:src/main/webapp/WEB-INF/app/jdbc-context.xml"
})
@Transactional(transactionManager = "transactionManager")
@Rollback(value = true)
@WebAppConfiguration
public class TestBidController {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testLoadOpenProductsWithAmountFilter() throws Exception {
        mockMvc.perform(get("/bidder/auctions/list")
                .sessionAttr("loggedInUsername", "bidder1")
                .sessionAttr("loggedInUserType", "bidder")
                .param("keyword", "Sap")
                .param("minPrice", "1000")
                .param("maxPrice", "60000"))
               .andExpect(status().isOk())
               .andExpect(view().name("bidderProductListView"));
    }

    @Test
    public void testLoadProductDetail() throws Exception {
        mockMvc.perform(get("/bidder/auctions/detail")
                .sessionAttr("loggedInUsername", "bidder1")
                .sessionAttr("loggedInUserType", "bidder")
                .param("productId", "1"))
               .andExpect(status().isOk())
               .andExpect(view().name("bidderProductDetailView"));
    }

    @Test
    public void testBidValidationFailure() throws Exception {
        mockMvc.perform(post("/bidder/auctions/bid")
                .sessionAttr("loggedInUsername", "bidder1")
                .sessionAttr("loggedInUserType", "bidder")
                .param("productId", "1")
                .param("bidPrice", ""))
               .andExpect(status().isOk())
               .andExpect(model().hasErrors())
               .andExpect(view().name("bidderProductDetailView"));
    }
    
    @Test
    public void testLoadMyBids() throws Exception {
        mockMvc.perform(get("/bidder/auctions/my-bids")
                .sessionAttr("loggedInUsername", "bidder1")
                .sessionAttr("loggedInUserType", "bidder"))
               .andExpect(status().isOk())
               .andExpect(view().name("bidderMyBidsView"));
    }
    
    @Test
    public void testLoadModifyBidForm() throws Exception {
        mockMvc.perform(get("/bidder/auctions/modify")
                .sessionAttr("loggedInUsername", "bidder1")
                .sessionAttr("loggedInUserType", "bidder")
                .param("bidId", "1"))
               .andExpect(status().isOk())
               .andExpect(view().name("bidderModifyBidView"));
    }
    
    /*
    @Test
    public void testSubmitModifyBidRedirects() throws Exception {
        mockMvc.perform(post("/bidder/auctions/modify")
                .sessionAttr("loggedInUsername", "bidder1")
                .sessionAttr("loggedInUserType", "bidder")
                .param("bidId", "1")
                .param("productId", "1")
                .param("bidPrice", "560000"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/app/bidder/auctions/my-bids"));
    }
	*/

    /*
    @Test
    public void testValidBidRedirects() throws Exception {
        mockMvc.perform(post("/bidder/auctions/bid")
                .sessionAttr("loggedInUsername", "bidder1")
                .sessionAttr("loggedInUserType", "bidder")
                .param("productId", "1")
                .param("bidPrice", "560000"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/app/bidder/auctions/detail?productId=1&success=1"));
    }*/
}
