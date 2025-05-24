package io.chiheb.financeservice.finance.clients.events;

import io.chiheb.financeservice.finance.domain.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class PaymentConfirmationEvent {
  private final String orderId;

  public PaymentConfirmationEvent(Transaction transaction) {
    orderId = transaction.getOrderId();
  }
}