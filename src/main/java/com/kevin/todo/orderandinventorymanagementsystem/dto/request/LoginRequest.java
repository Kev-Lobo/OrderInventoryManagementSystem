package com.kevin.todo.orderandinventorymanagementsystem.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {
    private String userName;
    private String password;
}
