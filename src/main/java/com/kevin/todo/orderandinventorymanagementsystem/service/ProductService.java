package com.kevin.todo.orderandinventorymanagementsystem.service;

import com.kevin.todo.orderandinventorymanagementsystem.dto.request.ProductRequest;
import com.kevin.todo.orderandinventorymanagementsystem.dto.response.ProductResponse;
import com.kevin.todo.orderandinventorymanagementsystem.entity.ProductEntity;
import com.kevin.todo.orderandinventorymanagementsystem.repository.ProductRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryService inventoryService;

    public ProductResponse createProduct(ProductRequest productRequest) {

        ProductEntity productEntity = new ProductEntity();
        productEntity.setProdName(productRequest.getProdName());
        productEntity.setProdPrice(productRequest.getProdPrice());
        productEntity.setProdCategory(productRequest.getProdCategory());
        productEntity.setProdDescription(productRequest.getProdDescription());
        productEntity.setProdCreatedAt(LocalDateTime.now());
        ProductEntity save = productRepository.save(productEntity);

        inventoryService.initializeStock(save.getProdId());
        return toResponse(save);
    }
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    public ProductEntity findByProdName(String prodName) {
        return productRepository.findByProdName(prodName);
    }

    public ProductResponse getProductById(ObjectId prodId) {
        ProductEntity product = productRepository.findById(prodId)
                .orElseThrow(() -> new RuntimeException("Product not found: " + prodId));
        return toResponse(product);
    }

    private ProductResponse toResponse(ProductEntity product) {
        return ProductResponse.builder()
                .prodId(product.getProdId().toString())
                .prodName(product.getProdName())
                .prodDescription(product.getProdDescription())
                .prodPrice(product.getProdPrice())
                .prodCategory(product.getProdCategory())
                .prodCreatedAt(product.getProdCreatedAt())
                .build();
    }
}
