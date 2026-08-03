package com.nak.demo.Controller;

import com.nak.demo.Model.BaseResponseModel;
import com.nak.demo.Model.BaseResponseModelWithData;
import com.nak.demo.Service.OrderService;
import com.nak.demo.dto.order.OrderCreateDto;
import com.nak.demo.dto.order.OrderResponseDto;
import com.nak.demo.dto.order.OrderUpdateDto;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<BaseResponseModel> placeOrder(@Valid @RequestBody OrderCreateDto payload){
        return orderService.createOrder(payload);
    }
    @GetMapping
    public ResponseEntity<BaseResponseModelWithData> listOrders(){
        return orderService.listsOrder();
    }
    @PatchMapping("/{id}")
    public ResponseEntity<BaseResponseModelWithData> updateOrderStatus(@PathVariable ("id") Long orderId, @RequestBody OrderUpdateDto payload){
        return orderService.updateOrderStatus(orderId,payload);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseModel> deleteOrder(@PathVariable ("id") Long orderId){
        return orderService.deleteOrder(orderId);
    }
}
