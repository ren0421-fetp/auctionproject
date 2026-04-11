package org.fujitsu.training.codes.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
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
public class TestSellerProfileController {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testLoadSellerProfile() throws Exception {
        mockMvc.perform(get("/seller/profile")
                .sessionAttr("loggedInUsername", "seller1")
                .sessionAttr("loggedInUserType", "seller"))
               .andExpect(status().isOk())
               .andExpect(view().name("sellerProfileView"));
    }

    @Test
    public void testUpdateSellerProfileValidationFailure() throws Exception {
        MockMultipartFile emptyPhoto = new MockMultipartFile("photoFile", "", "image/jpeg", new byte[0]);

        mockMvc.perform(multipart("/seller/profile/update")
                .file(emptyPhoto)
                .sessionAttr("loggedInUsername", "seller1")
                .sessionAttr("loggedInUserType", "seller")
                .param("username", "seller1")
                .param("firstName", "")
                .param("lastName", "")
                .param("gender", "")
                .param("address", "")
                .param("countryId", "")
                .param("stateId", "")
                .param("cityId", "")
                .param("email", "")
                .param("contactNo", ""))
               .andExpect(status().isOk())
               .andExpect(model().hasErrors())
               .andExpect(view().name("sellerProfileView"));
    }
    
    
    @Test
    public void testChangePasswordValidationFailure() throws Exception {
        mockMvc.perform(post("/seller/profile/change-password")
                .sessionAttr("loggedInUsername", "seller1")
                .sessionAttr("loggedInUserType", "seller")
                .param("currentPassword", "")
                .param("newPassword", "123")
                .param("confirmPassword", "456"))
               .andExpect(status().isOk())
               .andExpect(model().hasErrors())
               .andExpect(view().name("sellerProfileView"));
    }
}
