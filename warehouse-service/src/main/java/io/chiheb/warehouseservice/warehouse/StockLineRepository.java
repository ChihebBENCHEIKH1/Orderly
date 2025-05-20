package io.chiheb.warehouseservice.warehouse;

import io.chiheb.warehouseservice.warehouse.domain.StockLine;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

interface StockLineRepository extends ReactiveMongoRepository<StockLine, String> {
}