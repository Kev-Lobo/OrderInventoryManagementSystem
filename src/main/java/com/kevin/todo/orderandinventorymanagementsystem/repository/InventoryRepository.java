package com.kevin.todo.orderandinventorymanagementsystem.repository;

import com.kevin.todo.orderandinventorymanagementsystem.entity.InventoryEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface InventoryRepository extends MongoRepository<InventoryEntity, ObjectId> {
    Optional<InventoryEntity> findByProdId(ObjectId productId);
    void deleteByProdId(ObjectId productId);
}
