package org.fujitsu.training.codes.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.fujitsu.training.codes.exceptions.InvalidCredentialsException;
import org.fujitsu.training.codes.model.data.User;
import org.fujitsu.training.codes.model.form.LoginForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {
    "file:src/main/webapp/WEB-INF/app/context.xml",
    "file:src/main/webapp/WEB-INF/app/jdbc-context.xml"
})
@Transactional(transactionManager = "transactionManager")
@Rollback(true)
@WebAppConfiguration
public class TestLoginDaoImpl {

    @Autowired
    private LoginDaoImpl dao;
   
    @Test
    public void testLoginValidCredentials() throws Exception {
        LoginForm form = new LoginForm();
        form.setUsername("admin");
        form.setPassword("admin123");

        User user = dao.login(form);

        assertEquals("admin", user.getUsername());
    }

    @Test
    public void testLoginInvalidUsername() {
        LoginForm form = new LoginForm();
        form.setUsername("adminnn");
        form.setPassword("admin123");

        assertThrows(InvalidCredentialsException.class, () -> {
            dao.login(form);
        });
    }
    
    @Test
    public void testLoginInvalidPasswordThrowsException() {
        LoginForm form = new LoginForm();
        form.setUsername("admin");
        form.setPassword("wrong_password");

        assertThrows(InvalidCredentialsException.class, () -> {
            dao.login(form);
        });
    }
}