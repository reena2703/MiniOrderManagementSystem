package com.example.MiniOrderManagement.service;

import com.example.MiniOrderManagement.model.Customer;
import com.example.MiniOrderManagement.repository.CustomerRepository;
import com.example.MiniOrderManagement.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(String id) {
        return customerRepository.findById(id);
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(String id, Customer customerDetails) {
        return customerRepository.findById(id).map(customer -> {
            customer.setName(customerDetails.getName());
            customer.setEmail(customerDetails.getEmail());
            customer.setPhone(customerDetails.getPhone());
            customer.setAddress(customerDetails.getAddress());
            return customerRepository.save(customer);
        }).orElse(null);
    }

    // Cascade delete: delete customer and their orders
    public void deleteCustomer(String id) {
        customerRepository.findById(id).ifPresent(customer -> {
            orderRepository.deleteAll(orderRepository.findByCustomerEmail(customer.getEmail()));
            customerRepository.deleteById(id);
        });
    }
}