package org.jshaw.manner.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private SpringTemplateEngine templateEngine;
    @Autowired
    private JavaMailSender mailSender;

    @Async
    @Override
    public void sendEmailWithTemplate(String to, String templateName, Map<String, Object> model) {
        Context context = new Context();
        if (model != null) {
            context.setVariables(model);
        }

        logger.info("resolve template: mails/" + templateName);
        String htmlContent = templateEngine.process("mails/" + templateName, context);

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setSubject("Email Notification Test");
            message.setFrom("admin@jshaw.org", "Notification - DO NOT REPLY");
            message.setTo(to);
            message.setText(htmlContent, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        logger.info("done sending email");
    }
}
