package com.kevin.todo.orderandinventorymanagementsystem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
        private String prodName;
        private String prodDescription;
        private double prodPrice;
        private String prodCategory;
        private LocalDateTime prodCreatedAt;
}
