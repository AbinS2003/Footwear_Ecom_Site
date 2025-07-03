package com.abin.Footwear.E_com.controller;

import com.abin.Footwear.E_com.model.Size;
import com.abin.Footwear.E_com.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/size")
public class SizeController {

    @Autowired
    SizeRepository sizeRepository;

    @GetMapping("")
    public ResponseEntity<List<Size>> getAllSizes() {

        List<Size> allSizes = sizeRepository.findAll();

        if (allSizes.isEmpty()){
            ResponseEntity.status(404).body("Not found");
        }

        return ResponseEntity.ok(allSizes);
    }


}
