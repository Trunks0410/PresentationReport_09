package com.demo.orderprocessing.service;

import com.demo.orderprocessing.strategy.DiscountStrategy;
import com.demo.orderprocessing.strategy.DiscountStrategyRegistry;
import com.demo.orderprocessing.strategy.DiscountType;
import com.demo.orderprocessing.template.OrderProcessingResult;
import com.demo.orderprocessing.template.StandardOrderProcessor;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
  private final DiscountStrategyRegistry registry;

  public OrderService(DiscountStrategyRegistry registry) {
    this.registry = registry;
  }

  public OrderProcessingResult process(BigDecimal price, DiscountType discountType) {
    DiscountStrategy strategy = registry.get(discountType);

    // KẾT HỢP:
    // - Strategy được chọn từ request.
    // - Template (OrderProcessor) dùng Strategy đó ở bước applyDiscount().
    StandardOrderProcessor processor = new StandardOrderProcessor(strategy);
    return processor.processOrder(price);
  }
}

