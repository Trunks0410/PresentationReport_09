package com.demo.orderprocessing.controller;

import com.demo.orderprocessing.service.OrderService;
import com.demo.orderprocessing.strategy.DiscountType;
import com.demo.orderprocessing.template.OrderProcessingResult;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "*")
public class OrderController {
  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping("/process")
  public OrderProcessingResult process(@Valid @RequestBody OrderProcessRequest request) {
    return orderService.process(request.price(), request.discountType());
  }

  public record OrderProcessRequest(
      @NotNull(message = "Giá đơn hàng không được để trống.")
      @Positive(message = "Giá đơn hàng phải lớn hơn 0.")
      BigDecimal price,

      @NotNull(message = "Bạn cần chọn loại khuyến mãi.")
      DiscountType discountType
  ) {}
}

