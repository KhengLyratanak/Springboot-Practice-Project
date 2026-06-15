package com.nak.demo.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

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
