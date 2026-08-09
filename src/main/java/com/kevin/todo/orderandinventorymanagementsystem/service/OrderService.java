package com.kevin.todo.orderandinventorymanagementsystem.service;

import com.kevin.todo.orderandinventorymanagementsystem.dto.request.OrderRequest;
import com.kevin.todo.orderandinventorymanagementsystem.dto.response.OrderItemResponse;
import com.kevin.todo.orderandinventorymanagementsystem.dto.response.OrderResponse;
import com.kevin.todo.orderandinventorymanagementsystem.entity.*;
import com.kevin.todo.orderandinventorymanagementsystem.enums.OrderStatus;
import com.kevin.todo.orderandinventorymanagementsystem.repository.OrdersRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class OrderService {

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private InventoryService inventoryService;

    public OrderResponse placeOrder(OrderRequest orderRequest) {
        UserEntity userEntity = userService.findByUserName(orderRequest.getUserName());
        ProductEntity productEntity = productService.findByProdName(orderRequest.getProdName());

        inventoryService.decreaseStock(String.valueOf(productEntity.getProdId()), orderRequest.getQuantity());

        OrderItem orderItem = new OrderItem();
        orderItem.setProdId(productEntity.getProdId());
        orderItem.setQuantity(orderRequest.getQuantity());
        orderItem.setUnitPrice(productEntity.getProdPrice());

        Orders orders = new Orders();
        orders.setItems(new ArrayList<>(List.of(orderItem)));
        orders.setTotalAmount(productEntity.getProdPrice() * orderItem.getQuantity());
        orders.setUserId(userEntity.getUserId());
        orders.setStatus(OrderStatus.PENDING);
        orders.setCreatedAt(LocalDateTime.now());
        orders.setUpdatedAt(LocalDateTime.now());

        Orders savedOrder = ordersRepository.save(orders);

        if (userEntity.getOrders() == null) {
            userEntity.setOrders(new ArrayList<>());
        }
        userEntity.getOrders().add(savedOrder.getOrderId());
        userService.updateUser(userEntity);

        return toResponse(savedOrder);
    }

    public List<Orders> getOrdersByUsername(String userName) {
        UserEntity userEntity = userService.findByUserName(userName);
        return ordersRepository.findByUserId(userEntity.getUserId());
    }

    public OrderResponse getOrderById(String orderId) {
        Orders order = ordersRepository.findById(new ObjectId(orderId))
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));
        return toResponse(order);
    }

    public List<OrderResponse> getAllOrders() {
        return ordersRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public OrderResponse updateOrderStatus(String orderId, OrderStatus newStatus) {
        Orders order = ordersRepository.findById(new ObjectId(orderId))
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));
        order.setStatus(newStatus);
        order.setUpdatedAt(LocalDateTime.now());
        Orders updated = ordersRepository.save(order);
        return toResponse(updated);
    }

    public OrderResponse toResponse(Orders order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(item -> OrderItemResponse.builder()
                        .prodId(item.getProdId())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .build())
                .toList();

        return OrderResponse.builder()
                .orderId(order.getOrderId().toString())
                .userId(order.getUserId().toString())
                .items(itemResponses)
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus().name())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

}
