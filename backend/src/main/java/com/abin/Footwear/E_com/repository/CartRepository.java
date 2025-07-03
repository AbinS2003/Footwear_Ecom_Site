package com.abin.Footwear.E_com.repository;

import com.abin.Footwear.E_com.model.Cart;
import com.abin.Footwear.E_com.model.Product;
import com.abin.Footwear.E_com.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUserId(Long userId);

    boolean existsByUserIdAndProductId(Long userId, Long productId);

    boolean existsByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(User user, Product product);

    int countByUserId(Long userId);
}
