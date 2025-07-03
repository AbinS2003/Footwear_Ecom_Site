package com.abin.Footwear.E_com.repository;

import com.abin.Footwear.E_com.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String Email);

    Optional<User> findById(Long userId);
}
