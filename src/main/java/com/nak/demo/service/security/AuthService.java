package com.nak.demo.service.security;


import com.nak.demo.dto.user.UserDto;
import com.nak.demo.entity.User;
import com.nak.demo.exception.model.DuplicateException;
import com.nak.demo.mapper.UserMapper;
import com.nak.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private JwtUtil jwtUtil;

    public String register(UserDto payload){

        if(userRepository.existsByName(payload.getName())){
            throw new DuplicateException("user already existed");
        }

        if (userRepository.existsByEmail(payload.getEmail())) {
            throw new DuplicateException("email already existed");
        }
            User user = mapper.toEntity(payload);
            user.setPassword(passwordEncoder.encode(user.getPassword()));

     User createdUser = userRepository.save(user);
     String token = jwtUtil.generateToken(createdUser);
     return token;
    }
}
