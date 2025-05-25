package io.chiheb.warehouseservice.warehouse.repositories;

import io.chiheb.warehouseservice.warehouse.domain.Shipment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ShipmentRepository extends ReactiveMongoRepository<Shipment, String> {
}