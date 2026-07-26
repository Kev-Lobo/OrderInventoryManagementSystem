package com.kevin.todo.orderandinventorymanagementsystem.dto.request;

import lombok.Data;

@Data
public class OrderRequest {
    private String userName;
    private String prodName;
    private int quantity;
}
