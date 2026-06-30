package com.nak.demo.mapper;

import com.nak.demo.Entity.Order;
import com.nak.demo.Entity.OrderItem;
import com.nak.demo.dto.order.OrderCreateDto;
import com.nak.demo.dto.order.OrderItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component

public class OrderMapper {
    @Autowired
    private OrderItemMapper orderItemMapper;
    public Order toEntity(OrderCreateDto dto) {
        Order entity = new Order();

        List<OrderItem> orderItemEntities = dto.getOrderItems()
                .stream()
                .map(orderItemDto -> {
                    OrderItem orderItem = orderItemMapper.toEntity(orderItemDto);
                    orderItem.setOrder(entity);
                    return orderItem;
                })
                .toList();
        entity.setItems(orderItemEntities);
        return entity;
    }
}
