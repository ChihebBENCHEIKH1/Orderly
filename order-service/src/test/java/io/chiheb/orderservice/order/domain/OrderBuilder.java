
package io.chiheb.orderservice.order.domain;

import static io.chiheb.orderservice.order.domain.OrderStatus.INITIATED;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class OrderBuilder {

  public static Order.OrderBuilder get() {
    var address = Address.builder()
        .line1("line1")
        .line2("line2")
        .city("city")
        .country("country")
        .county("county")
        .postcode("postcode")
        .build();
    var paymentDetails = PaymentDetails.builder()
        .nameOnCard("Joan Merkley")
        .cardType("Visa")
        .cardNumber("4038838805770816")
        .cvv(197)
        .expires("07/2023")
        .build();
    return Order.builder()
        .id(UUID.randomUUID().toString())
        .customerId(UUID.randomUUID().toString())
        .orderLines(List.of(new OrderLine(UUID.randomUUID().toString(), 1)))
        .shippingAddress(address)
        .dateCreated(Instant.now())
        .paymentDetails(paymentDetails)
        .status(INITIATED)
        .billingAddress(address);
  }
}
