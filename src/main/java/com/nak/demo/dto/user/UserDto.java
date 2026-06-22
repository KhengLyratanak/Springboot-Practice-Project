package com.nak.demo.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private  Long id;
    @NotNull(message = "user name is required")
    private String name;

    private String password;

    private Integer age;

    private String address;

    private  String email;
    private String role= "USER";
}