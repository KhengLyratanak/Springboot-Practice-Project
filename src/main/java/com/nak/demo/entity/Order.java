package com.nak.demo.entity;

import com.nak.demo.common.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "orders")
@Data

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status = OrderStatus.PENDING.name();

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem> items;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
    this.createdAt = LocalDateTime.now();}

    @PreUpdate
    public void preUpdate(){ 
        this.updatedAt = LocalDateTime.now();}
    }
