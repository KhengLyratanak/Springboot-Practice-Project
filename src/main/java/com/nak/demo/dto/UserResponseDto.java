package com.nak.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Data
@JsonPropertyOrder({"user_id","user_name","email","age","location","role","created_at","updated_at"})
public class UserResponseDto {
    @JsonProperty("user_id")
    private Long id;
    @JsonProperty("user_name")
    private String userName;
    private Integer age;
    @JsonProperty("location")
    private String address;
    private String email;
    private String role ="USER";

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
