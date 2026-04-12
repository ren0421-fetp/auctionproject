package org.fujitsu.training.codes.dao.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.fujitsu.training.codes.model.data.UserPackageInfo;
import org.fujitsu.training.codes.model.form.UserPackageForm;
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
public class TestAdminUserPackageDaoImpl {

    @Autowired
    private AdminUserPackageDaoImpl dao;

    @Test
    public void testGetAllUserPackageInfos() {
        List<UserPackageInfo> infos = dao.getAllUserPackageInfos();
        assertNotNull(infos);
        assertTrue(!infos.isEmpty());
    }

    @Test
    public void testAssignPackage() throws Exception {
        UserPackageForm form = new UserPackageForm();
        form.setUsername("bidder1");
        form.setPackageId(1);

        dao.assignPackage(form);
    }
}
