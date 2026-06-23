package com.nak.demo.Service;

import com.nak.demo.Entity.User;
import com.nak.demo.Model.BaseResponseModel;
import com.nak.demo.Model.BaseResponseModelWithData;
import com.nak.demo.dto.user.ChangePasswordDto;
import com.nak.demo.dto.user.UpdateUserDto;
import com.nak.demo.exception.model.DuplicateException;
import com.nak.demo.exception.model.ResourceNotFoundException;
import com.nak.demo.dto.user.UserDto;
import com.nak.demo.Repository.UserRepository;

import com.nak.demo.dto.user.UserResponseDto;
import com.nak.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper mapper;

    public ResponseEntity<BaseResponseModelWithData> listUser(){
        List<User> users = userRepository.findAll();
        List<UserResponseDto> dtos = mapper.toDtoList(users);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModelWithData("success","successfully retrieve user",dtos));
    }
    public ResponseEntity<BaseResponseModelWithData> getUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("user not found with id :"  +userId));

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new BaseResponseModelWithData("success","user found : ",user));
    }
    public ResponseEntity<BaseResponseModel> createUser(UserDto payload){
        if(userRepository.existsByName(payload.getName())){
             throw new DuplicateException("user already existed");
        }
        if (userRepository.existsByEmail(payload.getEmail())){
            throw new DuplicateException("email already existed");
        }
        User user = mapper.toEntity(payload);

        userRepository.save(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new BaseResponseModel("success","successfully created user"));
    }
    public ResponseEntity<BaseResponseModel> updateUser(UpdateUserDto payload, Long userId){
       User existing = userRepository.findById(userId)
//IF USER NOT FOUND then response 404
        .orElseThrow(() ->
             new ResourceNotFoundException("user not found with id :"  +userId ));

      mapper.updateEntityFromDto(existing,payload);

       userRepository.save(existing);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success","successfully updated user"));
    }
    public ResponseEntity<BaseResponseModel> deleteUser( Long userId) {

        if(!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("user not found with id :"  +userId);
        }

        // user found , then delete
        userRepository.deleteById(userId);

        // 200 OK
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success","successfully deleted user"));
    }
    public ResponseEntity<BaseResponseModelWithData> findUser(String name){
        String formattedName = name  !=null ?
                name.toLowerCase()
                :name;
        List<User> user = userRepository.findUserWithFilters(formattedName);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModelWithData("success","successfully retrieved User", user));
    }
    public ResponseEntity<BaseResponseModel> changePassword( ChangePasswordDto dto,Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("user not found with id :" +userId));

        if (!Objects.equals(user.getPassword(),dto.getOldPassword())){
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT)
                        .body(new BaseResponseModel("fail" , "old password is incorrect"));
        }
        if (!Objects.equals(dto.getNewPassword(),dto.getConfirmPassword())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new BaseResponseModel("fail","new password and confirm password must be the same"));
        }
        mapper.updateEntityChangePassword(user,dto.getNewPassword());
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success","successfully changed password"));
    }

}
