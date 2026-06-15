package com.nak.demo.dto.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@JsonPropertyOrder({"stock_id","product_if","qty","created_at","updated_at"})
public class StockResponseDto {
    @JsonProperty("stock_id")
    private Long id;
    @JsonProperty("product_id")
    private Long productId;
    @JsonProperty("qty")
    private Long quantity;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("update_at")
    private LocalDateTime updatedAt;
}
