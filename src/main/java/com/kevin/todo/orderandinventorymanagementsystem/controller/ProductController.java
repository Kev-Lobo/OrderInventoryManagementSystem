package com.kevin.todo.orderandinventorymanagementsystem.controller;

import com.kevin.todo.orderandinventorymanagementsystem.dto.request.ProductRequest;
import com.kevin.todo.orderandinventorymanagementsystem.dto.response.ProductResponse;
import com.kevin.todo.orderandinventorymanagementsystem.service.ProductService;
import com.kevin.todo.orderandinventorymanagementsystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create-product")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> createProduct( @RequestBody ProductRequest productRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            ProductResponse createdProduct = productService.createProduct(productRequest);
            log.info("Product'{}' Created by ADMIN'{}'",createdProduct.getProdName(),username );
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/get-products")
    public ResponseEntity<?> getProducts() {
        try{
            return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-by/{prodId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable ObjectId prodId) {
        return ResponseEntity.ok(productService.getProductById(prodId));
    }

    @DeleteMapping("/delete/{prodId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProductById(@PathVariable ObjectId prodId) {
        try {
            productService.deleteProduct(prodId);
            return ResponseEntity.ok("Product and it's Inventory has been deleted successfully");
        }
        catch(IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch(Exception e) {
            log.error("Error in deleting product {}", prodId, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not delete product ");
        }
    }

}
