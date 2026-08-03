package com.nak.demo.Service;

import com.nak.demo.Entity.Order;
import com.nak.demo.Entity.Product;
import com.nak.demo.Entity.Stock;
import com.nak.demo.Model.BaseResponseModel;
import com.nak.demo.Model.BaseResponseModelWithData;
import com.nak.demo.Repository.OrderRepository;
import com.nak.demo.Repository.StockRepository;
import com.nak.demo.dto.order.OrderCreateDto;
import com.nak.demo.dto.order.OrderItemDto;
import com.nak.demo.dto.order.OrderUpdateDto;
import com.nak.demo.exception.model.ResourceNotFoundException;
import com.nak.demo.exception.model.UnprocessableEntityException;
import com.nak.demo.mapper.OrderMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StockRepository stockRepository;

    public ResponseEntity<BaseResponseModelWithData> listsOrder(){
        List<Order> orders = orderRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModelWithData("success","successfully retrieved orders",orderMapper.toResponseDtoList(orders)));
    }

    @Transactional
    public ResponseEntity<BaseResponseModel> createOrder(OrderCreateDto payload){

        //map for product ids
        List<Long> productIds = payload.getOrderItems().stream()
                .map(item -> item.getProductId())
                .toList();

        //get stocks in productids
        List<Stock> stocks = stockRepository.findByProductIdIn(productIds,
                Sort.by(Sort.Direction.ASC,"createdAt"));

        //map for required qty of productIds
        Map<Long,Long> requiredQuantities = payload.getOrderItems().stream()
                .collect(Collectors.toMap(OrderItemDto::getProductId,OrderItemDto::getAmount));
        //deduct stock for each product
        for (Long productId : requiredQuantities.keySet()){
            //quantity to deduct
            long remain = requiredQuantities.get(productId);

            //filter stock by productId
            List<Stock> stockByProduct = stocks.stream()
                    .filter(stock -> stock.getProduct().getId().equals(productId))
                    .toList();

            //calculate and compare qty
            for (Stock stock : stockByProduct){
                if (remain <= 0) break;

                long available = stock.getQuantity();

                if (available >= remain){
                    stock.setQuantity(available - remain);
                    remain = 0;
                }else {
                    stock.setQuantity(0L);
                    remain -= available;
                }
            }
            if (remain>0) {
                throw new UnprocessableEntityException("Not enough stock for product id : " +productId);
            }
        }
        stockRepository.saveAll(stocks);

        Order order = orderMapper.toEntity(payload);
        orderRepository.save(order);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponseModel("success","Successfully created order"));
    }
    public ResponseEntity<BaseResponseModelWithData> updateOrderStatus(Long orderId, OrderUpdateDto payload){
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow( () ->
                        new ResourceNotFoundException("Order not found with id :" +orderId));
        orderMapper.updateEntityDto(existingOrder,payload);

        orderRepository.save(existingOrder);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModelWithData("success","successfully updated order status",payload.getStatus()));
    }
    public ResponseEntity<BaseResponseModel> deleteOrder(Long orderId){
        if (!orderRepository.existsById(orderId)){
            throw new ResourceNotFoundException("order not found with id :"+orderId);
        }
        orderRepository.deleteById(orderId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success","successfully deleted order with id : " +orderId));
    }
}
