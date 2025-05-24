package io.chiheb.warehouseservice.warehouse.listeners.events;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
class OrderLine {
  private final String itemId;
  private final Integer quantity;
}