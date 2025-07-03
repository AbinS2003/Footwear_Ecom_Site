package com.abin.Footwear.E_com.controller;


import com.abin.Footwear.E_com.dto.AddToCartRequest;
import com.abin.Footwear.E_com.dto.CartItemDto;
import com.abin.Footwear.E_com.model.Cart;
import com.abin.Footwear.E_com.model.Product;
import com.abin.Footwear.E_com.model.Size;
import com.abin.Footwear.E_com.model.User;
import com.abin.Footwear.E_com.repository.CartRepository;
import com.abin.Footwear.E_com.repository.ProductRepository;
import com.abin.Footwear.E_com.repository.SizeRepository;
import com.abin.Footwear.E_com.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    SizeRepository sizeRepository;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody AddToCartRequest request) {

        if (cartRepository.existsByUserIdAndProductId(request.getUserId(), request.getProductId())) {
            return ResponseEntity.badRequest().body("Product already exists in cart");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Size size = sizeRepository.findById(request.getSizeId())
                .orElseThrow(() -> new RuntimeException("Size not found"));

        Cart cart = new Cart(user, product, size, request.getQuantity());
        cartRepository.save(cart);

        return ResponseEntity.ok("Product added to cart");
    }

    @GetMapping("/count/{userId}")
    public ResponseEntity<Integer> getCartItemCount(@PathVariable Long userId) {
        int count = cartRepository.countByUserId(userId);
        return ResponseEntity.ok(count);


    }


    @GetMapping("/user/{userId}")
    public List<CartItemDto> getCartItems(@PathVariable long userId) {
        List<Cart> cartItems = cartRepository.findByUserId(userId);
        List<CartItemDto> cartDtos = new ArrayList<>();

        for (Cart cart : cartItems) {
            Product product = cart.getProduct();
            CartItemDto dto = new CartItemDto(
                    cart.getId(),
                    product.getId(),
                    product.getTitle(),
                    product.getImage(),
                    product.getPrice(),
                    cart.getSize().getValue(),
                    cart.getQuantity()
            );
            cartDtos.add(dto);
        }

        return cartDtos;
    }


    @DeleteMapping("/remove/{userId}/{productId}")
    @Transactional
    public ResponseEntity<String> removeCartItem(@PathVariable Long userId, @PathVariable Long productId) {

       User user = userRepository.findById(userId)
               .orElseThrow(() -> new RuntimeException("User not found"));

       Product product = productRepository.findById(productId)
               .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!cartRepository.existsByUserAndProduct(user, product)){
            ResponseEntity.badRequest().body("Product not found in cart");
        }

        cartRepository.deleteByUserAndProduct(user, product);
        return ResponseEntity.ok("Removed from cart");

    }

}
