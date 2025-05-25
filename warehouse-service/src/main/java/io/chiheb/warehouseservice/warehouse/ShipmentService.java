package io.chiheb.warehouseservice.warehouse;

import io.chiheb.warehouseservice.warehouse.domain.Order;
import io.chiheb.warehouseservice.warehouse.domain.Shipment;
import io.chiheb.warehouseservice.warehouse.repositories.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ShipmentService {
  private final ShipmentRepository shipmentRepository;

  public Mono<Shipment> prepare(Order order) {
    return shipmentRepository.save(order.toShipment());
  }
}