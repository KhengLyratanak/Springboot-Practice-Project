package com.nak.demo.mapper;

import com.nak.demo.Entity.OrderItem;
import com.nak.demo.dto.order.OrderItemDto;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {
    public OrderItem toEntity (OrderItemDto dto){
        OrderItem entity = new OrderItem();

        entity.setProductId(dto.getProductId());
        entity.setQuantity(dto.getAmount());

        return entity;
    }
}
