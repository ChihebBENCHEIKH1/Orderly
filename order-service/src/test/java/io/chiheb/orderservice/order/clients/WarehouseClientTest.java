package io.chiheb.orderservice.order.clients;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import io.chiheb.orderservice.order.clients.events.StockReservationEvent;
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
class WarehouseServiceClientTest {

  @Mock
  KafkaTemplate<String, Object> kafkaTemplate;

  @InjectMocks
  WarehouseServiceClient warehouseServiceClient;

  @Captor
  ArgumentCaptor<StockReservationEvent> stockVerificationEventArgumentCaptor;

  @Test
  void sendStockReservationEvent() {
    var order = OrderBuilder.get().id("id1").build();

    warehouseServiceClient.sendStockReservationEvent(order);

    verify(kafkaTemplate).send(eq("warehouse.stock.reserve"), eq("id1-stock-reservation"),  stockVerificationEventArgumentCaptor.capture());

    var sentEvent = stockVerificationEventArgumentCaptor.getValue();
    assertThat(sentEvent.getOrderId()).isEqualTo(order.getId());
    assertThat(sentEvent.getOrderLines()).isEqualTo(order.getOrderLines());
  }
}