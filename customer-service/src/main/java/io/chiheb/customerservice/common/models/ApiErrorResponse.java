package io.chiheb.customerservice.common.models;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class ApiErrorResponse {
  private final String message;
}