package com.nak.demo.Model;

import lombok.Data;

@Data
public class BaseResponseModelWithData extends BaseResponseModel{
    private Object data;
    public BaseResponseModelWithData(String status, String message, Object data){
        super(status,message);
        this.data = data;
    }
}