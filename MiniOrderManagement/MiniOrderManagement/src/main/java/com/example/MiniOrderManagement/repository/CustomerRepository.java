package com.example.MiniOrderManagement.repository;

import com.example.MiniOrderManagement.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
import java.util.List;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByPhone(String phone);
    List<Customer> findByName(String name);

    // Optional: Uncomment if you want to search by address
    // List<Customer> findByAddress(String address);
}
