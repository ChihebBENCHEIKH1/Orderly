package io.chiheb.financeservice.finance.listeners;

import io.chiheb.financeservice.finance.FinanceService;
import io.chiheb.financeservice.finance.domain.Order;
import io.chiheb.financeservice.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static io.chiheb.financeservice.common.config.KafkaConfig.FINANCE_PAYMENT_PROCESS_TOPIC;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventsListener {
  private final FinanceService financeService;
  private final NotificationService notificationService;

  @KafkaListener(topics = FINANCE_PAYMENT_PROCESS_TOPIC)
  public void processPayment(@Payload Object event) {
    var order = (Order) event;
    log.info("received payment processing event for order {}", order.getId());
    financeService.processPayment(order)
        .doOnError(error -> financeService.rejectPayment(order.getId(), error.getMessage()))
        .doOnError(error -> notificationService.informCustomerAboutCancellation(order.getCustomerId(), order.getId(), error.getMessage()))
        .doOnSuccess(financeService::confirmPayment)
        .doOnSuccess(notificationService::informCustomerAboutPayment)
        .subscribe();
  }
}