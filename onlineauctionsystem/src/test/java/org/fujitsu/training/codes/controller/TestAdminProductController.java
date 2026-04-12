package org.fujitsu.training.codes.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
public class TestAdminProductController {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testLoadProductPage() throws Exception {
        mockMvc.perform(get("/admin/products")
                .sessionAttr("loggedInUsername", "admin1")
                .sessionAttr("loggedInUserType", "admin"))
               .andExpect(status().isOk())
               .andExpect(view().name("adminProductView"));
    }

    @Test
    public void testSaveProductRedirects() throws Exception {
        MockMultipartFile photoFile = new MockMultipartFile(
                "photoFile",
                "sample.jpg",
                "image/jpeg",
                "fake-image-content".getBytes()
        );

        mockMvc.perform(multipart("/admin/products/save")
                .file(photoFile)
                .sessionAttr("loggedInUsername", "admin1")
                .sessionAttr("loggedInUserType", "admin")
                .param("sellerUsername", "seller1")
                .param("productName", "admin test item")
                .param("description", "demo item")
                .param("catId", "1")
                .param("minBidPrice", "1000")
                .param("status", "open")
                .param("startDate", "2026-04-12T10:00")
                .param("endDate", "2026-04-13T10:00"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/app/admin/products?success=1"));
    }

    @Test
    public void testCloseProductRedirects() throws Exception {
        mockMvc.perform(post("/admin/products/close")
                .sessionAttr("loggedInUsername", "admin1")
                .sessionAttr("loggedInUserType", "admin")
                .param("productId", "2"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/app/admin/products?success=1"));
    }
}
