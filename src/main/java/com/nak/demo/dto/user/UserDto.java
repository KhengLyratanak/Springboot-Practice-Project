package com.nak.demo.dto.user;

import com.nak.demo.common.annotation.ValidEnum;
import com.nak.demo.common.enums.role;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotNull(message = "user name is required")
    @Size(min = 4,max = 30,message = "username must be between 4 and 30 charecters")
    private String name;

    @NotEmpty(message = "password is required")
    @Size(min = 8,max = 20,message = "address must be between 5 and 40 charecters")
    private String password;

    @NotNull(message = "age is required")
    @Min(value = 18,message = "age must be atleast 18")
    private Integer age;

    @NotNull(message = "location is required")
    @Size(min = 5,max = 50,message = "address must be between 5 and 50 charecters")
    private String address;

    @NotNull(message = "email is required")
    @Email(message = "email must be valid")
    private  String email;
    @NotNull(message = "role is required")
    @ValidEnum(enumClass = role.class,message = "Role must be in [USER,ADMIN]")
    private String role;
}