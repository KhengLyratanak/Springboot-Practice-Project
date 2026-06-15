package com.nak.demo.dto.supplier;

import lombok.Data;

@Data
public class SupplierDto {
    private Long id;
    private String name;
    private String address;
    private String rating;
    private String email;
    private Long phone;

}
