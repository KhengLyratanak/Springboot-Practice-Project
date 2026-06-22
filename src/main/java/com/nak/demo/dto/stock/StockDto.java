package com.nak.demo.dto.stock;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StockDto {
    @NotNull(message = "product id is required")
    private Long productId;
    @NotNull(message = "quantity is required")
    private Long quantity;
}
