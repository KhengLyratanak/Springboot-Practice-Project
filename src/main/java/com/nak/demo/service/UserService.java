package com.nak.demo.service;

import com.nak.demo.entity.User;
import com.nak.demo.dto.user.ChangePasswordDto;
import com.nak.demo.dto.user.UpdateUserDto;
import com.nak.demo.exception.model.DuplicateException;
import com.nak.demo.exception.model.ResourceNotFoundException;
import com.nak.demo.dto.user.UserDto;
import com.nak.demo.repository.UserRepository;

import com.nak.demo.dto.user.UserResponseDto;
import com.nak.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper mapper;

    public List<UserResponseDto> listUser(){
        List<User> users = userRepository.findAll();
        List<UserResponseDto> dtos = mapper.toDtoList(users);
        return mapper.toDtoList(users);
    }
    public UserResponseDto getUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("user not found with id :"  +userId));

            return mapper.toDto(user);
    }
    public void createUser(UserDto payload){
        if(userRepository.existsByName(payload.getName())){
             throw new DuplicateException("user already existed");
        }
        if (userRepository.existsByEmail(payload.getEmail())){
            throw new DuplicateException("email already existed");
        }
        User user = mapper.toEntity(payload);

        userRepository.save(user);

    }
    public void updateUser(UpdateUserDto payload, Long userId){
       User existing = userRepository.findById(userId)
//IF USER NOT FOUND then response 404
        .orElseThrow(() ->
             new ResourceNotFoundException("user not found with id :"  +userId ));

      mapper.updateEntityFromDto(existing,payload);

       userRepository.save(existing);

    }
    public void deleteUser( Long userId) {

        if(!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("user not found with id :"  +userId);
        }

        // user found , then delete
        userRepository.deleteById(userId);


    }
    public List<UserResponseDto> findUser(String name){
        String formattedName = name  !=null ?
                name.toLowerCase()
                :name;
        List<User> user = userRepository.findUserWithFilters(formattedName);
       return mapper.toDtoList(user);
    }
    public void changePassword(ChangePasswordDto dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("user not found with id :" + userId));
        //if current password not the same with oldpassword
        if (!Objects.equals(user.getPassword(), dto.getOldPassword())) {
            throw new ResourceNotFoundException("old password is incorrect,please return the correct password");
        }
        if (!Objects.equals(dto.getNewPassword(), dto.getConfirmPassword())) {
            throw new ResourceNotFoundException("new password and confirm password must be the same");
        }
        mapper.updateEntityChangePassword(user, dto.getNewPassword());
        userRepository.save(user);

    }

}
