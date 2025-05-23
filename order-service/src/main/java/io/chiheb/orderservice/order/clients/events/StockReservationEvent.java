package io.chiheb.orderservice.order.clients.events;

import io.chiheb.orderservice.order.domain.Order;
import io.chiheb.orderservice.order.domain.OrderLine;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@RequiredArgsConstructor
public class StockReservationEvent {
  private final String orderId;
  private final List<OrderLine> orderLines;

  public StockReservationEvent(Order order) {
    this.orderId = order.getId();
    this.orderLines = order.getOrderLines();
  }
}