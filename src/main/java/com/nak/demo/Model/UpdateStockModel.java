package com.nak.demo.Model;

import lombok.Data;

@Data
public class UpdateStockModel {
    private Integer operationType;
    private Long quantity;
}
