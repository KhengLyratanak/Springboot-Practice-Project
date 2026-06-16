package com.nak.demo.dto.supplier;

import lombok.Data;

@Data
public class SupplierDto {
    private String name;
    private String address;
    private String rating;
    private Long phone;
    private String email;
}
