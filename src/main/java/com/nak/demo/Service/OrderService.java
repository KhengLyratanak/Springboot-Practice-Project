package com.nak.demo.Service;

import com.nak.demo.Entity.Order;
import com.nak.demo.Model.BaseResponseModel;
import com.nak.demo.Repository.OrderRepository;
import com.nak.demo.dto.order.OrderCreateDto;
import com.nak.demo.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderRepository orderRepository;

    public ResponseEntity<BaseResponseModel> createOrder(OrderCreateDto payload){
        Order order = orderMapper.toEntity(payload);

        orderRepository.save(order);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponseModel("success","Successfully created order"));
    }
}
