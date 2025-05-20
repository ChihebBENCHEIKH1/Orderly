package io.chiheb.orderservice.order;

import io.chiheb.orderservice.order.domain.Order;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

interface OrderRepository extends ReactiveMongoRepository<Order, String> {
}