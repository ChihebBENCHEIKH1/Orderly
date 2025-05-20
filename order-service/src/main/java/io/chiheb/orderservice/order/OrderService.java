package io.chiheb.orderservice.order;

import io.chiheb.orderservice.order.clients.WarehouseServiceClient;
import io.chiheb.orderservice.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;
  private final WarehouseServiceClient warehouseServiceClient;

  public Mono<Order> create(Order order) {
    return orderRepository.save(order);
  }

  public void reserveStock(Order order) {
    warehouseServiceClient.sendStockReservationEvent(order);
  }
}