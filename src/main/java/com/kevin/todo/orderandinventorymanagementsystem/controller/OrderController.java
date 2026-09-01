package com.kevin.todo.orderandinventorymanagementsystem.controller;

import com.kevin.todo.orderandinventorymanagementsystem.dto.request.OrderRequest;
import com.kevin.todo.orderandinventorymanagementsystem.dto.response.OrderResponse;
import com.kevin.todo.orderandinventorymanagementsystem.entity.Orders;
import com.kevin.todo.orderandinventorymanagementsystem.entity.UserEntity;
import com.kevin.todo.orderandinventorymanagementsystem.service.OrderService;
import com.kevin.todo.orderandinventorymanagementsystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/create-orders")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest, Authentication authentication) throws Exception {
        orderRequest.setUserName(authentication.getName());
        try{
            OrderResponse orderResponse = orderService.placeOrder(orderRequest);
            return new ResponseEntity<>(orderResponse,HttpStatus.CREATED);
        }catch(IllegalArgumentException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            log.error("Unexpected error while placing the order :", e);
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-orders/{userName}")
    public ResponseEntity<List<Orders>> getOrdersByUsername(@PathVariable String userName, Authentication authentication) throws Exception {
        if(!isSelfOrAdmin(userName, authentication)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<Orders> orders = orderService.getOrdersByUsername(userName);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/process-orders/{userName}")
    public ResponseEntity<List<Orders>> processOrdersByUsername(@PathVariable String userName,Authentication authentication) {
        if(!isSelfOrAdmin(userName, authentication)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<Orders> orders = orderService.getOrdersByUsername(userName);
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/get-orders-id/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable String orderId, Authentication authentication) throws Exception {
        OrderResponse orderResponse = orderService.getOrderById(orderId);
        if(!isOwnerOrAdmin(orderResponse.getUserId(),authentication)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @DeleteMapping("/cancel/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable String orderId, Authentication authentication) throws Exception {
        try{
            OrderResponse orderResponse = orderService.getOrderById(orderId);
            if(!isOwnerOrAdmin(orderResponse.getUserId(),authentication)){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            orderService.cancelOrder(orderId);
            return ResponseEntity.ok("Order has been cancelled");
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch(IllegalStateException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        catch(Exception e){
            log.error("Error while canceling the order {} ",orderId, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not cancel the order");
        }
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    private boolean isSelfOrAdmin(String targetUserName, Authentication authentication) {
        return isAdmin(authentication) || authentication.getName().equals(targetUserName);
    }


    private boolean isOwnerOrAdmin(String targetUserId, Authentication authentication) {
        if(isAdmin(authentication)) {
            return true;
        }
        UserEntity byUserName = userService.findByUserName(authentication.getName());
        return byUserName != null && byUserName.getUserId().toString().equals(targetUserId);
    }
}
