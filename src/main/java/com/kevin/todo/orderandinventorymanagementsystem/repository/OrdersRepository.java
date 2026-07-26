package com.kevin.todo.orderandinventorymanagementsystem.repository;

import com.kevin.todo.orderandinventorymanagementsystem.entity.Orders;
import com.kevin.todo.orderandinventorymanagementsystem.entity.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrdersRepository extends MongoRepository<Orders, ObjectId> {
    List<Orders> findByUserId(ObjectId userId);
}
