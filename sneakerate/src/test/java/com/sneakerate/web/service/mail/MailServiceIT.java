package com.sneakerate.web.service.mail;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MailServiceIT {
    @Autowired
    private MailService mailService;

    @Test
    public void testSend() throws Exception {
        Assertions.assertThat(mailService.send("Test message")).isTrue();
    }

}