package org.jshaw.manner.service;

import java.util.Map;

public interface EmailService {

    void sendEmailWithTemplate(String to, String templateName, Map<String, Object> model);

}
