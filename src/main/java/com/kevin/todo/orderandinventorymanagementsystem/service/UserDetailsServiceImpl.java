package com.kevin.todo.orderandinventorymanagementsystem.service;

import com.kevin.todo.orderandinventorymanagementsystem.entity.UserEntity;
import com.kevin.todo.orderandinventorymanagementsystem.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserEntity userEntity = userRepository.findByUserName(username);
            if (userEntity != null) {
                return org.springframework.security.core.userdetails.User.builder()
                        .username(userEntity.getUserName())
                        .password(userEntity.getPassword())
                        .roles(userEntity.getRoles().toArray(new String[0]))
                        .build();
            }
            throw new UsernameNotFoundException("User not found with username: " + username);
        }catch (UsernameNotFoundException e) {
            log.warn("Login attempt failed - user not found: {}", username);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while loading user: {}", username, e);
            throw new RuntimeException("Error loading user: " + username, e);
        }
    }
}
