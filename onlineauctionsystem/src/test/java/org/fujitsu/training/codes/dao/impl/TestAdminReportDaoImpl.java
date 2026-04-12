package org.fujitsu.training.codes.dao.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
public class TestAdminReportDaoImpl {

    @Autowired
    private AdminReportDaoImpl dao;

    @Test
    public void testUserRegistrationReport() {
        assertNotNull(dao.getUserRegistrationReport());
    }

    @Test
    public void testAuctionItemReport() {
        assertNotNull(dao.getAuctionItemReport());
    }

    @Test
    public void testBidReport() {
        assertNotNull(dao.getBidReport());
    }

    @Test
    public void testConfirmBidReport() {
        assertNotNull(dao.getConfirmBidReport());
    }
}
