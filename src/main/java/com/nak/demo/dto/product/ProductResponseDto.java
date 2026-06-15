package com.nak.demo.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.time.LocalDateTime;
@JsonPropertyOrder({"product_id","product_name","price","description","created_at","updated_at"})
@Data
public class ProductResponseDto {
    @JsonProperty("product_id")
    private Long id;
    @JsonProperty("product_name")
    private String productName;

    private Double price;
    @JsonProperty("total_stock")
    private Long totalStock;
    private String description;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
