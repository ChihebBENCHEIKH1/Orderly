package io.chiheb.financeservice.finance;

import io.chiheb.financeservice.finance.clients.CatalogueServiceClient;
import io.chiheb.financeservice.finance.clients.OrderServiceClient;
import io.chiheb.financeservice.finance.domain.Order;
import io.chiheb.financeservice.finance.domain.OrderLine;
import io.chiheb.financeservice.finance.domain.Invoice;
import io.chiheb.financeservice.finance.domain.InvoiceLine;
import io.chiheb.financeservice.finance.exceptions.PaymentProcessingError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class FinanceService {
  private static final Random RNG = new Random();

  private final CatalogueServiceClient catalogueServiceClient;
  private final InvoiceRepository invoiceRepository;
  private final OrderServiceClient orderServiceClient;

  public Mono<Invoice> processPayment(Order order) {
    return processPayment(order, RNG);
  }

  public Mono<Invoice> processPayment(Order order, Random rng) {
    /* Complex payment processing logic */
    return Flux.fromIterable(order.getOrderLines())
        .flatMap(this::createInvoiceLine)
        .collectList()
        .map(order::toInvoice)
        .flatMap(invoice -> rng.nextBoolean() ? invoiceRepository.save(invoice) : Mono.error(new PaymentProcessingError(order.getId())));
  }

  private Mono<InvoiceLine> createInvoiceLine(OrderLine orderLine) {
    return catalogueServiceClient.findProductItem(orderLine.getItemId())
        .map(item -> new InvoiceLine(item, orderLine.getQuantity()));
  }

  public void rejectPayment(String orderId, String message) {
    orderServiceClient.sendPaymentProcessingFailure(orderId, message);
  }

  public void confirmPayment(Invoice invoice) {
    orderServiceClient.sendPaymentProcessingSuccess(invoice.getOrderId());
  }
}