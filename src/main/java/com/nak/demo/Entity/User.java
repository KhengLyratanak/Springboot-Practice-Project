package com.nak.demo.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    @Column(name = "user_name")
    private String userName;
    private Integer age;
    private String address;
    private String role;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
