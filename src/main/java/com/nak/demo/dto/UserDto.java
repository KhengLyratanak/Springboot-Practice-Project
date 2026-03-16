package com.nak.demo.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private  Long id;
    private String name;
    private String password;
    private Integer age;
    private String address;
    private  String email;
    private String role= "USER";
}