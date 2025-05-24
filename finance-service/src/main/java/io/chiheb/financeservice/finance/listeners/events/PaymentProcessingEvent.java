package io.chiheb.financeservice.finance.listeners.events;

import io.chiheb.financeservice.finance.domain.Address;
import io.chiheb.financeservice.finance.domain.OrderLine;
import io.chiheb.financeservice.finance.domain.PaymentDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class PaymentProcessingEvent {
  private final String orderId;
  private final String customerId;
  private final List<OrderLine> orderLines;
  private final PaymentDetails paymentDetails;
  private final Address billingAddress;
}