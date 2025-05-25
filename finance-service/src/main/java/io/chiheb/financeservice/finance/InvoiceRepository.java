package io.chiheb.financeservice.finance;

import io.chiheb.financeservice.finance.domain.Invoice;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface InvoiceRepository extends ReactiveMongoRepository<Invoice, String> {
}