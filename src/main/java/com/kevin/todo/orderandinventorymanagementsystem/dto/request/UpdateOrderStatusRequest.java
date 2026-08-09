package com.kevin.todo.orderandinventorymanagementsystem.dto.request;

import com.kevin.todo.orderandinventorymanagementsystem.enums.OrderStatus;
import lombok.Data;

@Data
public class UpdateOrderStatusRequest {
    private OrderStatus status;
}
