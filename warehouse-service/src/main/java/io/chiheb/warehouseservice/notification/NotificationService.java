package io.chiheb.warehouseservice.notification;

import io.chiheb.warehouseservice.notification.clients.NotificationServiceClient;
import io.chiheb.warehouseservice.warehouse.domain.Shipment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
  private final NotificationServiceClient notificationServiceClient;

  public void informCustomerAboutCancellation(String customerId, String orderId, String message) {
    notificationServiceClient.sendOrderCancellationEvent(customerId, orderId, message);
  }

  public void informCustomerAboutShipment(Shipment shipment) {
    notificationServiceClient.sendShipmentPreparationEvent(shipment);
  }
}