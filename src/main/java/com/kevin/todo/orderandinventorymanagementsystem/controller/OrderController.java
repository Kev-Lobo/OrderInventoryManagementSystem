package com.kevin.todo.orderandinventorymanagementsystem.controller;

import com.kevin.todo.orderandinventorymanagementsystem.dto.request.OrderRequest;
import com.kevin.todo.orderandinventorymanagementsystem.dto.response.OrderResponse;
import com.kevin.todo.orderandinventorymanagementsystem.entity.Orders;
import com.kevin.todo.orderandinventorymanagementsystem.service.OrderService;
import com.kevin.todo.orderandinventorymanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/create-orders")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        try{
            OrderResponse orderResponse = orderService.placeOrder(orderRequest);
            return new ResponseEntity<>(orderResponse,HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-orders/{userName}")
    public ResponseEntity<List<Orders>> getOrdersByUsername(@PathVariable String userName) {
        List<Orders> orders = orderService.getOrdersByUsername(userName);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/process-orders/{userName}")
    public ResponseEntity<List<Orders>> processOrdersByUsername(@PathVariable String userName) {
        List<Orders> orders = orderService.getOrdersByUsername(userName);
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/get-orders-id/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }
}
