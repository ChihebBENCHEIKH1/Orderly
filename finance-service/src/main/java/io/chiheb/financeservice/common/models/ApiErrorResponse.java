package io.chiheb.financeservice.common.models;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class ApiErrorResponse {
  private final String message;
}