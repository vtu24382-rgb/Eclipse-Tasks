package com.chat.chatapp.controller;

import com.chat.chatapp.model.Product;
import com.chat.chatapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    // GET all products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    
    // GET product by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable int id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        Map<String, String> error = new HashMap<>();
        error.put("message", "Product not found with id: " + id);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    
    // POST - Create new product
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }
    
    // PUT - Update product
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        if (updatedProduct != null) {
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        }
        Map<String, String> error = new HashMap<>();
        error.put("message", "Product not found with id: " + id);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    
    // DELETE - Delete product
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable int id) {
        Map<String, String> response = new HashMap<>();
        if (productService.deleteProduct(id)) {
            response.put("message", "Product deleted successfully with id: " + id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("message", "Product not found with id: " + id);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    // GET products by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        List<Product> products = productService.getProductsByCategory(category);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    
    // GET search products by name
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String name) {
        List<Product> products = productService.searchProductsByName(name);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    
    // GET products below price
    @GetMapping("/price-below/{price}")
    public ResponseEntity<List<Product>> getProductsBelowPrice(@PathVariable double price) {
        List<Product> products = productService.getProductsBelowPrice(price);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    
    // GET products in stock
    @GetMapping("/in-stock")
    public ResponseEntity<List<Product>> getProductsInStock() {
        List<Product> products = productService.getProductsInStock();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}