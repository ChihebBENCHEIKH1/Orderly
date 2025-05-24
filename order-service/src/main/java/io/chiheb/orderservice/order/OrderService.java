package io.chiheb.orderservice.order;

import io.chiheb.orderservice.order.clients.FinanceServiceClient;
import io.chiheb.orderservice.order.clients.WarehouseServiceClient;
import io.chiheb.orderservice.order.domain.Order;
import io.chiheb.orderservice.order.domain.OrderStatus;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;
  private final WarehouseServiceClient warehouseServiceClient;
  private final FinanceServiceClient financeServiceClient;

  public Mono<Order> create(Order order) {
    return orderRepository.save(order);
  }

  public Mono<Order> updateStatus(String orderId, OrderStatus status) {
    return orderRepository.findById(orderId)
        .map(order -> order.withStatus(status).withDateUpdated(Instant.now()))
        .doOnNext(orderRepository::save);
  }

  public void reserveStock(Order order) {
    warehouseServiceClient.sendStockReservationEvent(order);
  }

  public void processPayment(Order order) {
    financeServiceClient.sendPaymentProcessingEvent(order);
  }

  public void notifyCustomer(Order order) {
  }
}