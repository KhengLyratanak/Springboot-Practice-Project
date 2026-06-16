package com.nak.demo.dto.supplier;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonPropertyOrder({"supplier_id","supplier_name","location","email","rating","created_at","updated_at"})
public class SupplierResponseDto {
    @JsonProperty("supplier_id")
    private Long id;
    @JsonProperty("supplier_name")
    private String name;
    @JsonProperty("location")
    private String address;
    @JsonProperty("email")
    private String email;
    @JsonProperty("rating")
    private String rating;
    @JsonProperty("contact_no")
    private Long phone;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("update_at")
    private LocalDateTime updatedAt;
}
