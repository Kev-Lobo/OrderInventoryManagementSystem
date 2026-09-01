package com.kevin.todo.orderandinventorymanagementsystem.controller;

import com.kevin.todo.orderandinventorymanagementsystem.dto.request.UpdateOrderStatusRequest;
import com.kevin.todo.orderandinventorymanagementsystem.dto.request.UserRequest;
import com.kevin.todo.orderandinventorymanagementsystem.dto.response.OrderResponse;
import com.kevin.todo.orderandinventorymanagementsystem.dto.response.UserResponse;
import com.kevin.todo.orderandinventorymanagementsystem.service.OrderService;
import com.kevin.todo.orderandinventorymanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> createAdmin(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.registerAdmin(userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PatchMapping("/orders/{orderId}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable String orderId, @RequestBody UpdateOrderStatusRequest request) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, request.getStatus()));
    }

}
