package io.chiheb.customerservice.customer.customer;;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

interface ProductItemRepository extends ReactiveMongoRepository<ProductItem, String> {
}