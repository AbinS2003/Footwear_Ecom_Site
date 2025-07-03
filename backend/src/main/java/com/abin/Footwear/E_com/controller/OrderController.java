package com.abin.Footwear.E_com.controller;

import com.abin.Footwear.E_com.dto.AddOrderRequest;
import com.abin.Footwear.E_com.dto.OrderResponseDTO;
import com.abin.Footwear.E_com.model.Order;
import com.abin.Footwear.E_com.model.Product;
import com.abin.Footwear.E_com.model.Size;
import com.abin.Footwear.E_com.model.User;
import com.abin.Footwear.E_com.repository.OrderRepository;
import com.abin.Footwear.E_com.repository.ProductRepository;
import com.abin.Footwear.E_com.repository.SizeRepository;
import com.abin.Footwear.E_com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    SizeRepository sizeRepository; // ✅ Add this

    @Autowired
    OrderRepository orderRepository;

    @PostMapping("/add")
    public ResponseEntity<String> addOrders(@RequestBody AddOrderRequest request) {

        Optional<User> user = userRepository.findById(request.getUserId());
        Optional<Product> product = productRepository.findById(request.getProductId());
        Optional<Size> size = sizeRepository.findById(request.getSizeId());

        if (user.isEmpty() || product.isEmpty() || size.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid user, product or size");
        }

        Order order = new Order(product.get(), user.get(), size.get(), LocalDate.now(), "Pending", request.getQuantity());
        orderRepository.save(order);

        return ResponseEntity.ok("Waiting for confirmation");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getOrders(@PathVariable Long userId) {

        List<Order> orders = orderRepository.findByUserIdOrderByIdDesc(userId);

        if (orders.isEmpty()) {
            return ResponseEntity.status(404).body("No orders found for this user");
        }

        List<OrderResponseDTO> response = orders.stream().map(order ->
                new OrderResponseDTO(
                        order.getId(),
                        order.getProduct().getTitle(),
                        order.getUser().getName(),
                        order.getSize().getValue(),
                        order.getStatus(),
                        order.getDate(),
                        order.getProduct().getPrice(),
                        order.getProduct().getImage(),
                        order.getProduct().getId(),
                        order.getQuantity()
                )
        ).toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/cancel/{userId}/{productId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long userId, @PathVariable Long productId) {
        List<Order> orders = orderRepository.findByUserIdAndProductId(userId, productId);

        if (orders.isEmpty()) {
            return ResponseEntity.status(404).body("Order not found for this user and product");
        }

        for (Order order : orders) {
            order.setStatus("Cancelled");
            orderRepository.save(order);
        }

        return ResponseEntity.ok("Order cancelled successfully");
    }

}
