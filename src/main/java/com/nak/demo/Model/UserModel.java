package com.nak.demo.Model;

import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import lombok.*;

import java.security.PublicKey;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private  Long id;
    private String name;
    private Integer age;
    private String address;
    private  String email;
    private String role= "USER";
}