package com.example.MiniOrderManagement.repository;

import com.example.MiniOrderManagement.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {
    // Find product by name (optional, if you want to search by name)
    Optional<Product> findByName(String name);

    // Find all products in a category (optional, for category-based queries)
    List<Product> findByCategory(String category);
}
