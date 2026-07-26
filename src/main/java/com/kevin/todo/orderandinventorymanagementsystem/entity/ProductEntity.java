package com.kevin.todo.orderandinventorymanagementsystem.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "products")
@Data
@NoArgsConstructor
public class ProductEntity {
    @Id
    private ObjectId prodId;
    @Indexed(unique = true)
    @NonNull
    private String prodName;
    private String prodDescription;
    private double prodPrice;
    private String prodCategory;
    private LocalDateTime prodCreatedAt;
}
