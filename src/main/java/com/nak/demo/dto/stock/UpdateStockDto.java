package com.nak.demo.dto.stock;

import lombok.Data;

@Data
public class  UpdateStockDto {
    private Integer operationType;
    private Long quantity;
}
