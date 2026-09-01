package com.kevin.todo.orderandinventorymanagementsystem.controller;

import com.kevin.todo.orderandinventorymanagementsystem.dto.response.InventoryResponse;
import com.kevin.todo.orderandinventorymanagementsystem.dto.request.SetStockRequest;
import com.kevin.todo.orderandinventorymanagementsystem.service.InventoryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@PreAuthorize("hasRole('ADMIN')")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/set")
    public ResponseEntity<InventoryResponse> setStock(@RequestBody SetStockRequest request) {
        InventoryResponse response = inventoryService.setStock(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-by/{prodId}")
    public ResponseEntity<InventoryResponse> getStock(@PathVariable ObjectId prodId) {
        InventoryResponse response = inventoryService.getStock(prodId);
        return ResponseEntity.ok(response);
    }

}
