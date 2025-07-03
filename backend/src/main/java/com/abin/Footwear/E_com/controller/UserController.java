package com.abin.Footwear.E_com.controller;


import com.abin.Footwear.E_com.dto.UpdatePasswordRequest;
import com.abin.Footwear.E_com.dto.UpdateUserProfileRequest;
import com.abin.Footwear.E_com.model.User;
import com.abin.Footwear.E_com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserProfile(@PathVariable long userId) {

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @PostMapping("/change-password")
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordRequest request) {

        Optional<User> optionalUser = userRepository.findById(request.getUserId());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("Password Updated");
    }

    @PutMapping("/update-profile/{userId}")
    public ResponseEntity<String> updateProfile(@PathVariable Long userId,@RequestBody UpdateUserProfileRequest request) {

        Optional<User> OptionalUser = userRepository.findById(userId);

        if (OptionalUser.isEmpty()){
          return   ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = OptionalUser.get();

        user.setName(request.getName());
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());

        userRepository.save(user);

        return ResponseEntity.ok("User profile updated successfully");
    }





}
