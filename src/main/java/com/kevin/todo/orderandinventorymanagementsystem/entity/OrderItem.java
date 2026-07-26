package com.kevin.todo.orderandinventorymanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
        private ObjectId prodId;
        private int quantity;
        private double unitPrice; // This field denotes the price of the product when the order item was created.
}