package com.kevin.todo.orderandinventorymanagementsystem.dto.response;

import lombok.Data;

@Data
public class InventoryResponse {
    private String prodId;
    private int availableQuantity;
    private int reservedQuantity;
}
