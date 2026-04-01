package com.demo.orderprocessing.template;

import java.math.BigDecimal;
import java.util.List;

public record OrderProcessingResult(
    BigDecimal giaBanDau,
    BigDecimal giaSauGiam,
    List<String> cacBuoc
) {}

