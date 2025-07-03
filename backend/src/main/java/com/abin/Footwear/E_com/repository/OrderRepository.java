package com.abin.Footwear.E_com.repository;

import com.abin.Footwear.E_com.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);


    List<Order> findByUserIdAndProductId(Long userId, Long productId);

    List<Order> findByUserIdOrderByIdDesc(Long userId);
}
