package com.nak.demo.Model;

import com.nak.demo.Entity.Product;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class BaseResponseModelWithData extends BaseResponseModel{
    private Object data;
    public BaseResponseModelWithData(String status, String message, Object data){
        super(status,message);
        this.data = data;
    }
}