package com.nak.demo.service;

import com.nak.demo.entity.Order;
import com.nak.demo.repository.OrderRepository;
import com.nak.demo.repository.StockRepository;
import com.nak.demo.dto.order.OrderCreateDto;
import com.nak.demo.dto.order.OrderResponseDto;
import com.nak.demo.dto.order.OrderUpdateDto;
import com.nak.demo.exception.model.ResourceNotFoundException;
import com.nak.demo.mapper.OrderMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockManagementService stockManagementService;

    public List<OrderResponseDto> listsOrder(){
        List<Order> orders = orderRepository.findAll();
       return orderMapper.toResponseDtoList(orders);
    }

    @Transactional
    public void createOrder(OrderCreateDto payload){

        //reserve stock for order
        stockManagementService.reserveStockforOrder(payload.getOrderItems());
        Order order = orderMapper.toEntity(payload);

        orderRepository.save(order);

    }
    public OrderResponseDto updateOrderStatus(Long orderId, OrderUpdateDto payload){
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow( () -> {
                      throw new  ResourceNotFoundException("Order not found with id :" +orderId);
                        });
        orderMapper.updateEntityDto(existingOrder,payload);
        orderRepository.save(existingOrder);

        return orderMapper.toResponseDto(existingOrder);
    }
    public void deleteOrder(Long orderId){
        if (!orderRepository.existsById(orderId)){
            throw new ResourceNotFoundException("order not found with id :"+orderId);
        }
        orderRepository.deleteById(orderId);

    }
}
