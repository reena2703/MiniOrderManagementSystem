package com.example.MiniOrderManagement.controller;

import com.example.MiniOrderManagement.model.Order;
import com.example.MiniOrderManagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> getAllOrders(
            @RequestParam(required = false) Double minTotal,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate
    ) {
        if (minTotal != null) {
            return orderService.getOrdersByMinTotal(minTotal);
        } else if (fromDate != null && toDate != null) {
            return orderService.getOrdersByDateRange(fromDate, toDate);
        } else {
            return orderService.getAllOrders();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable String id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<Order> searchOrders(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone
    ) {
        if (email != null) {
            return orderService.getOrdersByCustomerEmail(email);
        } else if (phone != null) {
            return orderService.getOrdersByCustomerPhone(phone);
        } else {
            return orderService.getAllOrders();
        }
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order created = orderService.createOrder(order);
        if (created == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}