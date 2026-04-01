package com.demo.orderprocessing.strategy;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * Registry để chọn Strategy mà không cần if-else phức tạp.
 * Dễ mở rộng: thêm Strategy mới chỉ cần tạo class implements DiscountStrategy + khai báo type().
 */
@Component
public class DiscountStrategyRegistry {
  private final Map<DiscountType, DiscountStrategy> byType;

  public DiscountStrategyRegistry(List<DiscountStrategy> strategies) {
    EnumMap<DiscountType, DiscountStrategy> map = new EnumMap<>(DiscountType.class);
    for (DiscountStrategy strategy : strategies) {
      map.put(strategy.type(), strategy);
    }
    this.byType = Map.copyOf(map);
  }

  public DiscountStrategy get(DiscountType type) {
    DiscountStrategy strategy = byType.get(type);
    if (strategy == null) {
      throw new IllegalArgumentException("Không hỗ trợ loại khuyến mãi: " + type);
    }
    return strategy;
  }
}

