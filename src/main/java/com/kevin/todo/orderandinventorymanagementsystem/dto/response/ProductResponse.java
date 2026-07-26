package com.kevin.todo.orderandinventorymanagementsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
        private String prodId;
        private String prodName;
        private String prodDescription;
        private double prodPrice;
        private String prodCategory;
        private LocalDateTime prodCreatedAt;
}
