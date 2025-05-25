
package io.chiheb.warehouseservice.warehouse.listeners;

import static io.chiheb.warehouseservice.common.configs.KafkaConfig.WAREHOUSE_STOCK_RESERVE_TOPIC;

import io.chiheb.warehouseservice.warehouse.WarehouseService;
import io.chiheb.warehouseservice.warehouse.domain.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockEventsListener {
  private final WarehouseService warehouseService;

  @KafkaListener(topics = WAREHOUSE_STOCK_RESERVE_TOPIC)
  public void reserveStock(@Payload Object event) {
    var order = (Order) event;
    log.info("received stock reservation event for order {}", order.getId());
    Flux.fromIterable(order.getOrderLines())
        .flatMap(warehouseService::verifyIsInStock)
        .thenMany(Flux.fromIterable(order.getOrderLines()))
        .doOnNext(warehouseService::reserveStock)
        .onErrorStop()
        .doOnError(error -> warehouseService.rejectStockReservation(order.getId(), error.getMessage()))
        .doOnComplete(() -> warehouseService.confirmStockReservation(order.getId()))
        .subscribe();
  }
}
