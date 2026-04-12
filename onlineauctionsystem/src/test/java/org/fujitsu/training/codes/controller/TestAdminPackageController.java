package org.fujitsu.training.codes.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

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
public class TestAdminPackageController {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testLoadPackagePage() throws Exception {
        mockMvc.perform(get("/admin/packages")
                .sessionAttr("loggedInUsername", "admin1")
                .sessionAttr("loggedInUserType", "admin"))
               .andExpect(status().isOk())
               .andExpect(view().name("adminPackageView"));
    }

    @Test
    public void testSavePackageRedirects() throws Exception {
        MockMultipartFile photoFile = new MockMultipartFile(
                "photoFile",
                "gold-package.jpg",
                "image/jpeg",
                "fake-image-content".getBytes()
        );

        mockMvc.perform(multipart("/admin/packages/save")
                .file(photoFile)
                .sessionAttr("loggedInUsername", "admin1")
                .sessionAttr("loggedInUserType", "admin")
                .param("packageName", "gold package")
                .param("packagePrice", "750")
                .param("allowedBidCount", "75"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/app/admin/packages?success=1"));
    }

}
