package com.nak.demo.mapper;

import com.nak.demo.entity.Product;
import com.nak.demo.entity.Stock;
import com.nak.demo.dto.stock.StockDto;
import com.nak.demo.dto.stock.StockResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StockMapper {
    public Stock toEntity(StockDto dto, Product product){
        Stock entity = new Stock();
        entity.setQuantity(dto.getQuantity());
        entity.setProduct(product);

        return entity;
    }
    public StockResponseDto toDto(Stock entity){
        StockResponseDto dto = new StockResponseDto();
        dto.setId(entity.getId());
        dto.setQuantity(entity.getQuantity());
        dto.setProductId(entity.getProduct().getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
    public List<StockResponseDto> toDtoList(List<Stock> entities){
        if (entities == null || entities.isEmpty()){
            return new ArrayList<>();
        }
        return entities.stream()
                .map( stock -> this.toDto(stock))
                .collect(Collectors.toList());
    }
}
