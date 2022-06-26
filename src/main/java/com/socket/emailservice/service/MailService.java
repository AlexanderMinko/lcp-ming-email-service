package com.socket.emailservice.service;

import com.lenovo.model.NotificationEmail;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

  private final JavaMailSender javaMailSender;
  private final TemplateEngine templateEngine;

  public void sendMail(NotificationEmail notificationEmail) {
    MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
      mimeMessageHelper.setFrom("socket@socket.com");
      mimeMessageHelper.setTo(notificationEmail.getRecipient());
      mimeMessageHelper.setSubject(notificationEmail.getSubject());
      mimeMessageHelper.setText(build(notificationEmail.getBody()), true);
    };
    javaMailSender.send(mimeMessagePreparator);
    log.info("In sendMail - mail send");
  }

  private String build(String message) {
    Context context = new Context();
    context.setVariable("message", message);
    return templateEngine.process("mailTemplate", context);
  }
}
