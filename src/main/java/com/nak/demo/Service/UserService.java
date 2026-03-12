package com.nak.demo.Service;

import com.nak.demo.Entity.User;
import com.nak.demo.Model.BaseResponseModel;
import com.nak.demo.Model.BaseResponseModelWithData;
import com.nak.demo.Model.UserModel;
import com.nak.demo.Model.UserResponseModel;
import com.nak.demo.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private List<UserModel> users = new ArrayList<>(Arrays.asList(
            new UserModel(1L,"mad",23,"PP","","")
    ));

    public ResponseEntity<BaseResponseModelWithData> listUser(){
        List<User> users = userRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModelWithData("success","successfully retrieve user",users));
    }
    public ResponseEntity<BaseResponseModelWithData> getUser(Long userId){
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModelWithData("fail","user not found with id :" +userId,"null"));
        }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new BaseResponseModelWithData("success","user found : ",user.get()));
    }
    public ResponseEntity<BaseResponseModel> createUser(UserModel payload){
        User user = new User();
        user.setName(payload.getName());
        user.setAddress(payload.getAddress());
        user.setAge(payload.getAge());
        user.setEmail(payload.getEmail());
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(payload.getRole());

        userRepository.save(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new BaseResponseModel("success","successfully created user"));
    }
    public ResponseEntity<BaseResponseModel> updateUser(UserModel payload,Long userId){
       Optional<User> existing = userRepository.findById(userId);
//IF USER NOT FOUND then response 404
       if (existing.isEmpty()){
           return ResponseEntity.status(HttpStatus.NOT_FOUND)
                   .body(new BaseResponseModel("Fail","user not found with id: " +userId));
       }
       User updatedUser = existing.get();
       updatedUser.setName(payload.getName());
       updatedUser.setAge(payload.getAge());
       updatedUser.setAddress(payload.getAddress());
       updatedUser.setRole(payload.getRole());
       updatedUser.setEmail(payload.getEmail());
       userRepository.save(updatedUser);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success","successfully updated user"));
    }
    public ResponseEntity<BaseResponseModel> deleteUser( Long userId) {

        if(!userRepository.existsById(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModel("fail","user not found with id: " + userId));
        }

        // user found , then delete
        userRepository.deleteById(userId);

        // 200 OK
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success","successfully deleted user"));
    }
}
