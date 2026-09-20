package com.nak.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_Token")
@Data
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "token",nullable = false)
    private String token ;
    @Column(name = "expires_At",nullable = false)
    private LocalDateTime expiresAt;
    @Column(name = "creaeted_At")
    private LocalDateTime createdAt;

    @Column(name = "revoked", nullable = false)
    private boolean revoked = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }

    public boolean isExpired(){
        return LocalDateTime.now().isAfter(this.expiresAt);
    }
    public boolean isValid(){
        return !this.isExpired() && !this.revoked;
    }

}
