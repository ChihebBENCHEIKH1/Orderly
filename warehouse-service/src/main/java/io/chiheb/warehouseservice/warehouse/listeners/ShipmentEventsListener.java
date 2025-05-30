package io.chiheb.warehouseservice.warehouse.listeners;

import io.chiheb.warehouseservice.notification.NotificationService;
import io.chiheb.warehouseservice.warehouse.ShipmentService;
import io.chiheb.warehouseservice.warehouse.domain.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static io.chiheb.warehouseservice.common.configs.KafkaConfig.WAREHOUSE_SHIPMENT_DISPATCH_TOPIC;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShipmentEventsListener {
  private final ShipmentService shipmentService;
  private final NotificationService notificationService;

  @KafkaListener(topics = WAREHOUSE_SHIPMENT_DISPATCH_TOPIC)
  public void dispatch(@Payload Object event) {
    var order = (Order) event;
    log.info("received shipment dispatch event for order {}", order.getId());
    shipmentService.prepare(order)
        .doOnNext(notificationService::informCustomerAboutShipment)
        .subscribe();
  }
}