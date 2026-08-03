package com.nak.demo.dto.order;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.nak.demo.common.annotation.ValidEnum;
import com.nak.demo.common.enums.OrderStatus;
import lombok.Data;

@Data
public class OrderUpdateDto {
    @JsonProperty("status")
    @ValidEnum(enumClass = OrderStatus.class,message = "value must be on of PENDING,FAIL,SUCCESS")
    private String status;
}
