package io.chiheb.financeservice.finance;

import io.chiheb.financeservice.finance.clients.CatalogueServiceClient;
import io.chiheb.financeservice.finance.clients.OrderServiceClient;
import io.chiheb.financeservice.finance.domain.OrderDetails;
import io.chiheb.financeservice.finance.domain.OrderLine;
import io.chiheb.financeservice.finance.domain.Transaction;
import io.chiheb.financeservice.finance.domain.TransactionLine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FinanceService {
  private final CatalogueServiceClient catalogueServiceClient;
  private final TransactionRepository transactionRepository;
  private final OrderServiceClient orderServiceClient;

  public Mono<Transaction> processPayment(OrderDetails order) {
    /* Heavy payment processing logic */
    return Flux.fromIterable(order.getOrderLines())
        .flatMap(this::createTransactionLine)
        .collectList()
        .map(order::toTransaction)
        .flatMap(transactionRepository::save);
  }

  private Mono<TransactionLine> createTransactionLine(OrderLine orderLine) {
    return catalogueServiceClient.findProductItem(orderLine.getItemId())
        .map(item -> new TransactionLine(item, orderLine.getQuantity()));
  }

  public void rejectPayment(String orderId, String message) {
    orderServiceClient.sendPaymentProcessingFailure(orderId, message);
  }

  public void confirmPayment(Transaction transaction) {
    orderServiceClient.sendPaymentProcessingSuccess(transaction);
  }
}