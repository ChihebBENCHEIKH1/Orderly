package io.chiheb.catalogueservice.product;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

interface ProductItemRepository extends ReactiveMongoRepository<ProductItem, String> {
}