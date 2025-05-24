package io.chiheb.financeservice.finance.domain;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class OrderLine {
  private final String itemId;
  private final Integer amount;
  private final Integer quantity;

}