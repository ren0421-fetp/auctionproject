package org.fujitsu.training.codes.dao.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.fujitsu.training.codes.dao.impl.RegistrationDaoImpl;
import org.fujitsu.training.codes.model.form.RegistrationForm;
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
public class TestRegistrationDaoImpl {
    
    @Autowired
    private RegistrationDaoImpl dao;
    
    @Test
    public void testRegisterUser() throws Exception {
        RegistrationForm form = new RegistrationForm();
        form.setUsername("newbidder99");
        form.setFirstName("Jane");
        form.setLastName("Smith");
        form.setGender("female");
        form.setAddress("456 Main St");
        form.setCountryId(1); 
        form.setStateId(1);
        form.setCityId(1);
        form.setEmail("janesmith@example.com");
        form.setContactNo("09876543210");
        form.setPassword("Pass123!");
        form.setUserType("bidder");
        form.setPhotoPath("/images/test.jpg");
        
        String result = dao.registerUser(form);
        assertNotNull(result);
        System.out.println("Registered User: " + result);
    }
}