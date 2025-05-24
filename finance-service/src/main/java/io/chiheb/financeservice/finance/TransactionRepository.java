package io.chiheb.financeservice.finance;

import io.chiheb.financeservice.finance.domain.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {
}