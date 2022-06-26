package com.socket.emailservice.event;

import static com.lenovo.utils.EventUtils.deserialize;

import com.lenovo.model.events.NotificationEmailEvent;

import com.socket.emailservice.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class EventConsumerService {

  private final MailService mailService;

  @KafkaListener(topics = {"lcp-ming-send-email-event"})
  public void consumeNotificationEmailEvent(String event) {
    log.debug("Consumed event: {}", event);
    var notificationEmailEvent = deserialize(event, NotificationEmailEvent.class);
    mailService.sendMail(notificationEmailEvent.getNotificationEmail());
  }
}
