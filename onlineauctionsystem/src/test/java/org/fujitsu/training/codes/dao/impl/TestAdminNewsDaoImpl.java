package org.fujitsu.training.codes.dao.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.fujitsu.training.codes.model.data.News;
import org.fujitsu.training.codes.model.form.NewsForm;
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
public class TestAdminNewsDaoImpl {

    @Autowired
    private AdminNewsDaoImpl dao;

    @Test
    public void testGetAllNews() {
        List<News> news = dao.getAllNews();
        assertNotNull(news);
    }

    @Test
    public void testSaveNews() throws Exception {
    	NewsForm form = new NewsForm();
    	form.setNewsTitle("Tomorrow holiday");
    	form.setNewsContent("Office will be closed tomorrow.");
    	dao.saveNews(form);
    }
}
