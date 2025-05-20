package io.chiheb.orderservice.order.domain;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class OrderLine {
  private final String itemId;
  private final Integer amount;
}