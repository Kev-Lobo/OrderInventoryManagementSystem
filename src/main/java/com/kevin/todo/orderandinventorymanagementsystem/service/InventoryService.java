package com.kevin.todo.orderandinventorymanagementsystem.service;


import com.kevin.todo.orderandinventorymanagementsystem.dto.response.InventoryResponse;
import com.kevin.todo.orderandinventorymanagementsystem.dto.request.SetStockRequest;
import com.kevin.todo.orderandinventorymanagementsystem.entity.InventoryEntity;
import com.kevin.todo.orderandinventorymanagementsystem.repository.InventoryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class InventoryService {
    
    @Autowired
    private InventoryRepository inventoryRepository;

    public InventoryResponse setStock(SetStockRequest setStockRequest){
        InventoryEntity inventoryEntity = inventoryRepository.findByProdId(setStockRequest.getProdId()).orElse(new InventoryEntity());
        inventoryEntity.setProdId(setStockRequest.getProdId());
        inventoryEntity.setAvailableQuantity(setStockRequest.getQuantity());
        InventoryEntity save = inventoryRepository.save(inventoryEntity);
        return toResponse(save);
    }

    public InventoryResponse getStock(ObjectId prodId){
        InventoryEntity inventoryEntity = inventoryRepository.findByProdId(prodId).orElseThrow(() -> new RuntimeException("The ProductId is not found" +prodId));
        return toResponse(inventoryEntity);
    }

    public void reserveStock(String productId, int quantity) {
        InventoryEntity inventory = inventoryRepository.findByProdId(new ObjectId(productId))
                .orElseThrow(() -> new RuntimeException("No inventory found for product: " + productId));

        if (inventory.getAvailableQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock for product: " + productId);
        }
        inventory.setAvailableQuantity(inventory.getAvailableQuantity() - quantity);
        inventory.setReservedQuantity(inventory.getReservedQuantity() + quantity);
        inventoryRepository.save(inventory);
    }

    public void releaseReservedStock(String productId, int quantity) {
        InventoryEntity inventory = inventoryRepository.findByProdId(new ObjectId(productId))
                .orElseThrow(() -> new RuntimeException("No inventory found for product: " + productId));
        int releaseReserved = Math.max(0, inventory.getReservedQuantity() - quantity);
        inventory.setReservedQuantity(releaseReserved);
        inventory.setAvailableQuantity(inventory.getAvailableQuantity() + quantity);
        inventoryRepository.save(inventory);
    }

    public void deleteByProdId(ObjectId productId) {
        inventoryRepository.deleteByProdId(productId);
    }

    public void initializeStock(ObjectId prodId) {
        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setProdId(prodId);
        inventoryEntity.setAvailableQuantity(0);
        inventoryEntity.setReservedQuantity(0);
        inventoryRepository.save(inventoryEntity);
    }

    public InventoryResponse toResponse(InventoryEntity inventoryEntity){
        InventoryResponse inventoryResponse = new InventoryResponse();
        inventoryResponse.setProdId(inventoryEntity.getProdId().toString());
        inventoryResponse.setAvailableQuantity(inventoryEntity.getAvailableQuantity());
        return inventoryResponse;
    }

}
