package com.kevin.todo.orderandinventorymanagementsystem.service;

import com.kevin.todo.orderandinventorymanagementsystem.dto.request.UserRequest;
import com.kevin.todo.orderandinventorymanagementsystem.dto.response.UserResponse;
import com.kevin.todo.orderandinventorymanagementsystem.entity.UserEntity;
import com.kevin.todo.orderandinventorymanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponse registerUser(UserRequest userRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userRequest.getUserName());
        userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setRoles(Arrays.asList("USER"));
        UserEntity save = userRepository.save(userEntity);
        return toResponse(save);
    }
    public UserResponse registerAdmin(UserRequest userRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userRequest.getUserName());
        userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setRoles(Arrays.asList("USER", "ADMIN"));
        UserEntity save = userRepository.save(userEntity);
        return toResponse(save);
    }

    public void updateUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    public UserEntity findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
    private UserResponse toResponse(UserEntity userEntity) {
        return UserResponse.builder()
                .userId(userEntity.getUserId().toString())
                .userName(userEntity.getUserName())
                .email(userEntity.getEmail())
                .orders(userEntity.getOrders())
                .build();
    }
}
/*
*  public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUserById(ObjectId id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUserById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public void updateUser(UserEntity user) {
        userRepository.save(user);
    }
    */

