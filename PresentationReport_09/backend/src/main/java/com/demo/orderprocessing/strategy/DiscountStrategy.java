package com.demo.orderprocessing.strategy;

import java.math.BigDecimal;

/**
 * STRATEGY PATTERN:
 * - Đây là interface Strategy.
 * - Mỗi loại khuyến mãi là một "chiến lược" tính giá sau giảm.
 */
public interface DiscountStrategy {
  DiscountType type();

  BigDecimal apply(BigDecimal originalPrice);

  String moTa();
}

