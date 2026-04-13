package org.fujitsu.training.codes.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart; // Changed from post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile; // Required for simulating file uploads
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(locations = {
        "file:src/main/webapp/WEB-INF/app/context.xml",
        "file:src/main/webapp/WEB-INF/app/jdbc-context.xml"
})
@Transactional(transactionManager = "transactionManager")
@Rollback(value = true)
@WebAppConfiguration
public class TestRegistrationController {
    
    private MockMvc mockMvc;
    
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @AfterEach
    public void teardown() {
        mockMvc = null;
    }
    
    @Test
    public void testLoadFormView() throws Exception {
        mockMvc.perform(get("/registration"))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(view().name("registerView"));
    }
 
    @Test
    public void testSubmitFormWithImageUpload() throws Exception {
        
        // 1. Create a simulated file upload
        // The first parameter "photoFile" MUST match the path in your form model
        MockMultipartFile simulatedPhoto = new MockMultipartFile(
                "photoFile", 
                "profile_pic.jpg", 
                "image/jpeg", 
                "fake-image-byte-content".getBytes()
        );

        // 2. Use multipart() instead of post() to simulate a multipart/form-data request
        mockMvc.perform(multipart("/registration")
                .file(simulatedPhoto) // Attach the file
                .param("username", "testuser9956")
                .param("firstName", "John")
                .param("lastName", "Doe")
                .param("gender", "M")
                .param("address", "123 Auction St")
                .param("countryId", "1")
                .param("stateId", "1")
                .param("cityId", "1")
                .param("email", "johndoe@example.com")
                .param("contactNo", "09123456789")
                .param("password", "Pass123!")
                .param("confirmPassword", "Pass123!")
                .param("userType", "bidder"))
               .andDo(print())
               .andExpect(view().name("redirect:/app/login")); 
    }
}