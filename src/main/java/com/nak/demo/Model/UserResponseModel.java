package com.nak.demo.Model;

import com.nak.demo.Entity.User;
import lombok.Data;

import java.util.List;
@Data
public class UserResponseModel extends BaseResponseModel{
    private List<User> users;
    public UserResponseModel (String status, String message,List<User> users){
        super(status,message);
        this.users = users;
    }
}
