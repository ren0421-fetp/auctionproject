package org.fujitsu.training.codes.dao.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.fujitsu.training.codes.model.data.Category;
import org.fujitsu.training.codes.model.form.CategoryForm;
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
public class TestAdminCategoryDaoImpl {

    @Autowired
    private AdminCategoryDaoImpl dao;

    @Test
    public void testGetAllCategories() {
        List<Category> categories = dao.getAllCategories();
        assertNotNull(categories);
        assertTrue(!categories.isEmpty());
    }
    
    
    @Test
    public void testSaveCategory() throws Exception {
        CategoryForm form = new CategoryForm();
        form.setCatName("Home Decor");

        dao.saveCategory(form);
    }
    
}
