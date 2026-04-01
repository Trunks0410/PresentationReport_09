package com.demo.orderprocessing.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Component;

@Component
public class PercentageDiscount implements DiscountStrategy {
  private static final BigDecimal TY_LE = new BigDecimal("0.10"); // 10%

  @Override
  public DiscountType type() {
    return DiscountType.PERCENTAGE;
  }

  @Override
  public BigDecimal apply(BigDecimal originalPrice) {
    BigDecimal discount = originalPrice.multiply(TY_LE);
    return originalPrice.subtract(discount).setScale(2, RoundingMode.HALF_UP);
  }

  @Override
  public String moTa() {
    return "Giảm 10%";
  }
}

