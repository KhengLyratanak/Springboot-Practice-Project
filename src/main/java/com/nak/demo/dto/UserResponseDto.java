package com.nak.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    @JsonProperty("user_id")
    private Long id;
    private String email;

    @JsonProperty("user_name")
    private String userName;

    private Integer age;
    @JsonProperty("location")
    private String address;
    private String role ="USER ";
}
