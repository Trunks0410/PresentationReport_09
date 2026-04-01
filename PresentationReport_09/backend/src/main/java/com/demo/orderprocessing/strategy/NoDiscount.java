package com.demo.orderprocessing.strategy;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class NoDiscount implements DiscountStrategy {
  @Override
  public DiscountType type() {
    return DiscountType.NONE;
  }

  @Override
  public BigDecimal apply(BigDecimal originalPrice) {
    return originalPrice;
  }

  @Override
  public String moTa() {
    return "Không giảm";
  }
}

