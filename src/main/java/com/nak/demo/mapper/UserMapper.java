package com.nak.demo.mapper;

import com.nak.demo.Entity.User;
import com.nak.demo.dto.UserDto;
import com.nak.demo.dto.UserResponseDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public User toEntity(UserDto dto){
        User entity = new User();

        entity.setUserName(dto.getName());
        entity.setPassword(dto.getPassword());
        entity.setRole(dto.getRole());
        entity.setAge(dto.getAge());
        entity.setAddress(dto.getAddress());
        entity.setEmail(dto.getEmail());
        entity.setCreatedAt(LocalDateTime.now());
        return entity;
    }
    public UserResponseDto toDto(User entity){
        UserResponseDto dto = new UserResponseDto();

        dto.setRole(entity.getRole());
        dto.setUserName(entity.getUserName());
        dto.setAge(entity.getAge() );
        dto.setEmail(entity.getEmail());
        dto.setAddress(entity.getAddress());

            return dto;

    }
    public List<UserResponseDto> toDtoList(List<User> entities){
        if (entities  == null || entities.isEmpty()){
            return new ArrayList<>();
        }
        return entities.stream()
                .map(user -> this.toDto(user))
                .collect(Collectors.toList());
    }
}
