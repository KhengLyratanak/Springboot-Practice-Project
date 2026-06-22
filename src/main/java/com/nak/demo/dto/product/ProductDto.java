package com.nak.demo.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    @NotBlank(message = "Product name is required")
    private String name;
    @NotNull(message = "Product price is required")
    @Positive(message = "Price must be positive ")
    private Double price;

    private String description;


}
