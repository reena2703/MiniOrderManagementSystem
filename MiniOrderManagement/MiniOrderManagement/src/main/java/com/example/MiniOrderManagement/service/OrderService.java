package com.example.MiniOrderManagement.service;

import com.example.MiniOrderManagement.model.*;
import com.example.MiniOrderManagement.repository.OrderRepository;
import com.example.MiniOrderManagement.repository.ProductRepository;
import com.example.MiniOrderManagement.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(String id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrdersByCustomerEmail(String email) {
        return orderRepository.findByCustomerEmail(email);
    }

    public List<Order> getOrdersByCustomerPhone(String phone) {
        return orderRepository.findByCustomerPhone(phone);
    }

    public List<Order> getOrdersByMinTotal(double minTotal) {
        return orderRepository.findByTotalAmountGreaterThanEqual(minTotal);
    }

    public List<Order> getOrdersByDateRange(Date from, Date to) {
        return orderRepository.findByOrderDateBetween(from, to);
    }

    public Order createOrder(Order order) {
        // Embed the latest customer info
        Optional<Customer> customerOpt = customerRepository.findById(order.getCustomer().getId());
        if (customerOpt.isEmpty()) return null;
        Customer customer = customerOpt.get();
        order.setCustomer(customer);

        // Calculate total and embed product snapshots
        double total = 0.0;
        for (OrderItem item : order.getItems()) {
            Optional<Product> productOpt = productRepository.findById(item.getProduct().getId());
            if (productOpt.isEmpty()) return null;
            Product product = productOpt.get();
            item.setProduct(product); // Embed product snapshot
            item.setSubtotal(product.getPrice() * item.getQuantity());
            total += item.getSubtotal();
        }
        order.setTotalAmount(total);

        // Set timestamps
        Date now = new Date();
        order.setOrderDate(now);
        order.setCreatedAt(now);
        order.setUpdatedAt(now);

        return orderRepository.save(order);
    }

    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
    }
}
