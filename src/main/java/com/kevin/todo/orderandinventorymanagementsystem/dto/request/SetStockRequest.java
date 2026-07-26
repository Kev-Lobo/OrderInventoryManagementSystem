package com.kevin.todo.orderandinventorymanagementsystem.dto.request;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class SetStockRequest {
    private ObjectId prodId;
    private int quantity;
}
