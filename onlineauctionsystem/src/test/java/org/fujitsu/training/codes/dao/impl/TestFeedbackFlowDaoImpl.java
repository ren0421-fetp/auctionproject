package org.fujitsu.training.codes.dao.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.fujitsu.training.codes.model.data.Feedback;
import org.fujitsu.training.codes.model.form.FeedbackForm;
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
public class TestFeedbackFlowDaoImpl {

    @Autowired
    private FeedbackFlowDaoImpl dao;

    @Test
    public void testGetAllFeedback() {
        List<Feedback> feedback = dao.getAllFeedback();
        assertNotNull(feedback);
    }

    @Test
    public void testSaveFeedback() throws Exception {
        FeedbackForm form = new FeedbackForm();
        form.setFirstName("nirav");
        form.setEmail("nirav@gmail.com");
        form.setContact("7383887633");
        form.setSubject("happy");
        form.setMsg("nice site");

        dao.saveFeedback(form);
    }
}
