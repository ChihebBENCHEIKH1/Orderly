package io.chiheb.orderservice.order.clients;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import io.chiheb.orderservice.order.clients.events.PaymentProcessingEvent;
import io.chiheb.orderservice.order.domain.OrderBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

@ExtendWith(MockitoExtension.class)
class FinanceServiceClientTest {

  @Mock
  KafkaTemplate<String, Object> kafkaTemplate;

  @InjectMocks
  FinanceServiceClient financeServiceClient;

  @Captor
  ArgumentCaptor<PaymentProcessingEvent> paymentProcessingEventArgumentCaptor;

  @Test
  void sendPaymentProcessingEvent() {
    var order = OrderBuilder.get().id("id1").build();

    financeServiceClient.sendPaymentProcessingEvent(order);

    verify(kafkaTemplate).send(eq("finance.payment.process"), eq("id1-payment-processing"),  paymentProcessingEventArgumentCaptor.capture());

    var sentEvent = paymentProcessingEventArgumentCaptor.getValue();
    assertThat(sentEvent)
        .extracting(
            PaymentProcessingEvent::getOrderId,
            PaymentProcessingEvent::getCustomerId,
            PaymentProcessingEvent::getPaymentDetails,
            PaymentProcessingEvent::getBillingAddress,
            PaymentProcessingEvent::getOrderLines
        )
        .containsExactly("id1", order.getCustomerId(), order.getPaymentDetails(), order.getBillingAddress(), order.getOrderLines());
  }
}