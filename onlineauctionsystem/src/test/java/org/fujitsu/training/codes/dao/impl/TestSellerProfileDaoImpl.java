package org.fujitsu.training.codes.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.fujitsu.training.codes.model.form.ChangePasswordForm;
import org.fujitsu.training.codes.model.form.SellerProfileForm;
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
public class TestSellerProfileDaoImpl {

    @Autowired
    private SellerProfileDaoImpl dao;

    @Test
    public void testLoadSellerProfile() {
        SellerProfileForm form = dao.getSellerProfileForm("seller1");
        //assertNotNull(form);
        assertEquals("seller1", form.getUsername());
    }

    @Test
    public void testUpdateSellerProfile() throws Exception {
        SellerProfileForm form = dao.getSellerProfileForm("seller1");
        form.setFirstName("UpdatedSeller");
        form.setLastName("Tester");
        form.setAddress("Updated Address");
        form.setEmail("seller1_updated@example.com");
        form.setContactNo("09998887777");

        dao.updateSellerProfile(form, "seller1");

        SellerProfileForm updated = dao.getSellerProfileForm("seller1");
        assertEquals("UpdatedSeller", updated.getFirstName());
    }
    
    /*
    @Test
    public void testChangeSellerPassword() throws Exception {
        ChangePasswordForm form = new ChangePasswordForm();
        form.setCurrentPassword("Pass123!");
        form.setNewPassword("NewPass123!");
        form.setConfirmPassword("NewPass123!");

        dao.changeSellerPassword(form, "seller1");
    }*/
}
