package com.chat.chatapp.repository;

import com.chat.chatapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    
    // Custom query methods
    List<Product> findByCategory(String category);
    
    List<Product> findByNameContainingIgnoreCase(String name);
    
    List<Product> findByPriceLessThan(double price);
    
    List<Product> findByQuantityGreaterThan(int quantity);
    
    List<Product> findByCategoryAndPriceLessThan(String category, double price);
}