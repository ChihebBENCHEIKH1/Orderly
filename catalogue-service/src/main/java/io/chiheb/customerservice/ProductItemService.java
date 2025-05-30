package io.chiheb.customerservice.customer.customer;;

import io.chiheb.catalogueservice.product.exceptions.ItemNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductItemService {
  private final ProductItemRepository productItemRepository;

  public Mono<ProductItem> get(String id) {
    return productItemRepository.findById(id)
        .switchIfEmpty(Mono.error(new ItemNotFound(id)));
  }
}