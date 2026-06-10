package com.nak.demo.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    private Double price;

    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Stock> stocks;

    @Transient
    public  Long getTotalStock(){
        if (stocks == null ) return 0L;

        return stocks.stream()
                .mapToLong(stock -> stock.getQuantity())
                .sum();
    }
    @PreUpdate
    public void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }
}
