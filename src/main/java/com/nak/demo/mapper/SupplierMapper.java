package com.nak.demo.mapper;

import com.nak.demo.Entity.Supplier;
import com.nak.demo.Entity.User;
import com.nak.demo.dto.UserDto;
import com.nak.demo.dto.supplier.SupplierDto;
import com.nak.demo.dto.supplier.SupplierResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SupplierMapper {
    public Supplier toEntity(SupplierDto dto){
        Supplier entity = new Supplier();

        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setEmail(dto.getEmail());
        entity.setRating(dto.getRating());

        return entity;
    }
    public SupplierResponseDto toDto(Supplier entity){
        if (entity == null){
            return null;
        }
        SupplierResponseDto dto = new SupplierResponseDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setRating(entity.getRating());
        dto.setAddress(entity.getAddress());
        dto.setCreatedAt(dto.getCreatedAt());
        dto.setUpdatedAt(dto.getUpdatedAt());
        return dto;
    }
    public void updateEntityFromDto(Supplier entity, SupplierDto dto){
        if (entity == null || dto == null){
            return;
        }
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setEmail(dto.getEmail());
        entity.setRating(dto.getRating());
        entity.
    }
    public List<SupplierResponseDto> toDtoList(List<Supplier> entities){
        if (entities == null || entities.isEmpty()){
            return new ArrayList<>();
        }
        return entities.stream()
                .map(supplier -> this.toDto(supplier))
                .collect(Collectors.toList());
    }
}