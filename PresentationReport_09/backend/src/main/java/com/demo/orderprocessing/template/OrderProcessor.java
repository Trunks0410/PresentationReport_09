package com.demo.orderprocessing.template;

import com.demo.orderprocessing.strategy.DiscountStrategy;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * TEMPLATE METHOD PATTERN:
 * - Abstract class định nghĩa "khung" xử lý đơn hàng.
 * - Template method: processOrder() (final) cố định thứ tự các bước.
 *
 * KẾT HỢP STRATEGY:
 * - OrderProcessor chứa DiscountStrategy (thành phần linh hoạt).
 * - Bước applyDiscount() sẽ ủy quyền tính toán cho Strategy đang được chọn.
 */
public abstract class OrderProcessor {
  protected final DiscountStrategy discountStrategy;

  protected OrderProcessor(DiscountStrategy discountStrategy) {
    this.discountStrategy = discountStrategy;
  }

  /**
   * Template method: KHÔNG cho override để giữ quy trình cố định.
   */
  public final OrderProcessingResult processOrder(BigDecimal price) {
    List<String> steps = new ArrayList<>();

    validateOrder(price, steps);
    BigDecimal afterDiscount = applyDiscount(price, steps);
    processPayment(afterDiscount, steps);
    sendConfirmation(afterDiscount, steps);

    return new OrderProcessingResult(price, afterDiscount, List.copyOf(steps));
  }

  protected abstract void validateOrder(BigDecimal price, List<String> steps);

  protected abstract BigDecimal applyDiscount(BigDecimal price, List<String> steps);

  protected abstract void processPayment(BigDecimal amountToPay, List<String> steps);

  protected abstract void sendConfirmation(BigDecimal finalAmount, List<String> steps);
}

