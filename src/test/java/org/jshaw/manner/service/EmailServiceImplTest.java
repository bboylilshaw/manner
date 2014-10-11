package org.jshaw.manner.service;

import org.jshaw.manner.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class EmailServiceImplTest {

    @Autowired
    EmailService emailService;

    @Test
    public void testSendEmailWithTemplate() throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("user", "Jason");
        emailService.sendEmailWithTemplate("xiaoyao8823@gmail.com", "email", model);
    }
}