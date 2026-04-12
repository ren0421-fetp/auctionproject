package org.fujitsu.training.codes.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
public class TestAdminNewsController {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testLoadNewsPage() throws Exception {
        mockMvc.perform(get("/admin/news")
                .sessionAttr("loggedInUsername", "admin1")
                .sessionAttr("loggedInUserType", "admin"))
               .andExpect(status().isOk())
               .andExpect(view().name("adminNewsView"));
    }

    @Test
    public void testSaveNewsRedirects() throws Exception {
        mockMvc.perform(post("/admin/news/save")
                .sessionAttr("loggedInUsername", "admin1")
                .sessionAttr("loggedInUserType", "admin")
                .param("newsTitle", "Tomorrow holiday")
                .param("newsContent", "Office will be closed tomorrow."))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/app/admin/news?success=1"));
    }

}
