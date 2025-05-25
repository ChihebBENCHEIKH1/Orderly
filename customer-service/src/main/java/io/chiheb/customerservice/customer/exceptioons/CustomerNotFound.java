package io.chiheb.customerservice.customer.exceptions;

import io.chiheb.customerservice.common.exceptions.ServiceApiException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class CustomerNotFound extends ServiceApiException {
  public CustomerNotFound(String customerId) {
    super(NOT_FOUND, String.format("customer with id %s does not exist", customerId));
  }
}