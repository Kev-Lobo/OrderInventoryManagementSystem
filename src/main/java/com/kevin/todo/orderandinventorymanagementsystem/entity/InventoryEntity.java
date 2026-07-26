package com.kevin.todo.orderandinventorymanagementsystem.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "inventory")
@Data
@NoArgsConstructor
public class InventoryEntity {
    @Id
    private ObjectId id;
    private ObjectId prodId;
    private int availableQuantity=0 ;
    private int reservedQuantity=0;
    private int version;
}
