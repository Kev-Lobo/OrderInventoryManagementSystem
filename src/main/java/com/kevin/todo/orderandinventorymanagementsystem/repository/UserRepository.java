package com.kevin.todo.orderandinventorymanagementsystem.repository;

import com.kevin.todo.orderandinventorymanagementsystem.entity.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, ObjectId> {
    UserEntity findByUserName(String userName);
}
