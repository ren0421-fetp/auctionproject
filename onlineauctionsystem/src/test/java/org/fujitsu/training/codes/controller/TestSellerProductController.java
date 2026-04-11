package org.fujitsu.training.codes.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
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
public class TestSellerProductController {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testLoadAddProductForm() throws Exception {
        mockMvc.perform(get("/seller/product/add")
                .sessionAttr("loggedInUsername", "seller1")
                .sessionAttr("loggedInUserType", "seller"))
               .andExpect(status().isOk())
               .andExpect(view().name("sellerProductFormView"));
    }

    @Test
    public void testAddProductValidationFailure() throws Exception {
        MockMultipartFile simulatedPhoto = new MockMultipartFile(
                "photoFile",
                "",
                "image/jpeg",
                new byte[0]
        );

        mockMvc.perform(multipart("/seller/product/save")
                .file(simulatedPhoto)
                .sessionAttr("loggedInUsername", "seller1")
                .sessionAttr("loggedInUserType", "seller")
                .param("productName", "")
                .param("description", "")
                .param("catId", "")
                .param("minBidPrice", "")
                .param("startDate", "")
                .param("endDate", "")
                .param("status", ""))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(model().hasErrors())
               .andExpect(view().name("sellerProductFormView"));
    }
    /*
    @Test
    public void testAddProductSuccess() throws Exception {
        MockMultipartFile simulatedPhoto = new MockMultipartFile(
                "photoFile",
                "auction_item.jpg",
                "image/jpeg",
                "fake-image-content".getBytes()
        );

        mockMvc.perform(multipart("/seller/product/save")
                .file(simulatedPhoto)
                .sessionAttr("loggedInUsername", "seller1")
                .sessionAttr("loggedInUserType", "seller")
                .param("productName", "Vintage Clock")
                .param("description", "A classic vintage clock")
                .param("catId", "1")
                .param("minBidPrice", "5000")
                .param("startDate", "2026-04-12T08:00")
                .param("endDate", "2026-04-20T08:00")
                .param("status", "open"))
               .andDo(print())
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/seller/product/list"));
    }*/
}
