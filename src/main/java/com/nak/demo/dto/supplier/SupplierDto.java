package com.nak.demo.dto.supplier;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SupplierDto {
    @NotNull(message = "supplier name is required")
    @NotBlank(message = "supplier name should not be blank")
    @Size(max = 30,message = "supplier name can not exceed 30")
    private String name;
    @NotNull(message = "location is required")
    private String address;
    @Size(message = "rating cant be exceed 40 charectors")
    private String rating;
    @NotNull(message = "phone number is required")
    @NotBlank(message = "phone number must not be blank")
    private String phone;
    @NotNull(message = "email is required")
    @Email(message = "email must be valid")
    @Size(max = 30,message = "email cannot exceed 30 charectors")
    private String email;
}
