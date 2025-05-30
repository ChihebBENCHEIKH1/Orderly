package io.chiheb.notificationservice.notification;

import io.chiheb.notificationservice.notification.clients.CustomerServiceClient;
import io.chiheb.notificationservice.notification.domain.Invoice;
import io.chiheb.notificationservice.notification.domain.Shipment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class NotificationService {
  private final CustomerServiceClient customerServiceClient;

  public Mono<Void> notifyAboutCancellation(String customerId, String orderId, String message) {
    return Mono.empty();
  }

  public Mono<Void> notifyAboutShipping(Shipment shipment) {
    return Mono.empty();
  }

  public Mono<Void> notifyAboutPayment(Invoice invoice) {
    return Mono.empty();
  }
}