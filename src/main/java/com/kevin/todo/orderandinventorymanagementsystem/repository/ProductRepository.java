package com.kevin.todo.orderandinventorymanagementsystem.repository;

import com.kevin.todo.orderandinventorymanagementsystem.entity.ProductEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<ProductEntity, ObjectId> {
    ProductEntity findByProdName(String prodName);
}
