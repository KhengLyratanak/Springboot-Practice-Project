package com.nak.demo.repository;

import com.nak.demo.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Ref;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
}
