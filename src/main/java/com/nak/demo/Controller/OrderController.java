package com.nak.demo.Controller;

import com.nak.demo.Model.BaseResponseModel;
import com.nak.demo.Model.BaseResponseModelWithData;
import com.nak.demo.Service.OrderService;
import com.nak.demo.dto.base.Response;
import com.nak.demo.dto.order.OrderCreateDto;
import com.nak.demo.dto.order.OrderResponseDto;
import com.nak.demo.dto.order.OrderUpdateDto;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Response> placeOrder(@Valid @RequestBody OrderCreateDto payload){
         orderService.createOrder(payload);

         return ResponseEntity.status(HttpStatus.CREATED)
                 .body(Response.success("201","sucess","successfully created order"));
    }
    @GetMapping
    public ResponseEntity<Response> listOrders(){
        List<OrderResponseDto> orders = orderService.listsOrder();
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200","success","successfully retrieved order",orders));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Response> updateOrderStatus(@PathVariable ("id") Long orderId, @RequestBody OrderUpdateDto payload){
      OrderResponseDto updatedOrder = orderService.updateOrderStatus(orderId,payload);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200","success","successfully updated order",updatedOrder));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteOrder(@PathVariable ("id") Long orderId){
         orderService.deleteOrder(orderId);
         return ResponseEntity.status(HttpStatus.OK)
                 .body(Response.success("200","success","successfully deleted order" +orderId));
    }
}
