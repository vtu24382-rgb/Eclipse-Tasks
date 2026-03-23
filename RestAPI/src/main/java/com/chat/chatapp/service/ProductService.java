package com.chat.chatapp.service;

import com.chat.chatapp.model.Product;
import com.chat.chatapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    // GET all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    // GET product by ID
    public Product getProductById(int id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }
    
    // POST - Create new product
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
    
    // PUT - Update existing product
    public Product updateProduct(int id, Product productDetails) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        
        if (existingProduct != null) {
            existingProduct.setName(productDetails.getName());
            existingProduct.setPrice(productDetails.getPrice());
            existingProduct.setQuantity(productDetails.getQuantity());
            existingProduct.setCategory(productDetails.getCategory());
            existingProduct.setDescription(productDetails.getDescription());
            return productRepository.save(existingProduct);
        }
        return null;
    }
    
    // DELETE - Delete product
    public boolean deleteProduct(int id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // GET products by category
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }
    
    // GET products by name search
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
    
    // GET products below price
    public List<Product> getProductsBelowPrice(double price) {
        return productRepository.findByPriceLessThan(price);
    }
    
    // GET products in stock
    public List<Product> getProductsInStock() {
        return productRepository.findByQuantityGreaterThan(0);
    }
}