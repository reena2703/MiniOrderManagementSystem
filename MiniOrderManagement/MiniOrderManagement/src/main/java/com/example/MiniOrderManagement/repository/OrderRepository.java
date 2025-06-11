package com.example.MiniOrderManagement.repository;

import com.example.MiniOrderManagement.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    // Find orders by customer email
    List<Order> findByCustomerEmail(String email);

    // Find orders by customer phone
    List<Order> findByCustomerPhone(String phone);

    // Find orders with totalAmount greater than or equal to a value
    List<Order> findByTotalAmountGreaterThanEqual(double minTotal);

    // Find orders between two dates
    List<Order> findByOrderDateBetween(Date from, Date to);
}
