package com.kevin.todo.orderandinventorymanagementsystem.controller;

import com.kevin.todo.orderandinventorymanagementsystem.dto.request.ProductRequest;
import com.kevin.todo.orderandinventorymanagementsystem.dto.response.ProductResponse;
import com.kevin.todo.orderandinventorymanagementsystem.service.ProductService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create-product")
    public ResponseEntity<ProductResponse> createProduct( @RequestBody ProductRequest productRequest) {
        try {
            ProductResponse createdProduct = productService.createProduct(productRequest);
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

}
