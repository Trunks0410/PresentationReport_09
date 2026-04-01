package com.demo.orderprocessing.template;

import com.demo.orderprocessing.strategy.DiscountStrategy;
import java.math.BigDecimal;
import java.util.List;

/**
 * Một triển khai cụ thể của Template:
 * - Có thể thay đổi chi tiết từng bước (validate/payment/confirmation),
 *   nhưng không thay đổi thứ tự vì processOrder() là final.
 */
public class StandardOrderProcessor extends OrderProcessor {

  public StandardOrderProcessor(DiscountStrategy discountStrategy) {
    super(discountStrategy);
  }

  @Override
  protected void validateOrder(BigDecimal price, List<String> steps) {
    steps.add("Kiểm tra đơn hàng");
    if (price == null) {
      throw new IllegalArgumentException("Giá đơn hàng không được để trống.");
    }
    if (price.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Giá đơn hàng phải lớn hơn 0.");
    }
  }

  @Override
  protected BigDecimal applyDiscount(BigDecimal price, List<String> steps) {
    steps.add("Áp dụng giảm giá");
    return discountStrategy.apply(price);
  }

  @Override
  protected void processPayment(BigDecimal amountToPay, List<String> steps) {
    steps.add("Thanh toán");
  }

  @Override
  protected void sendConfirmation(BigDecimal finalAmount, List<String> steps) {
    steps.add("Gửi xác nhận");
  }
}

