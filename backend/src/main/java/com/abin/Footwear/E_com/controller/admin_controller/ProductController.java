package com.abin.Footwear.E_com.controller.admin_controller;

import com.abin.Footwear.E_com.dto.admin_dto.AddProductRequest;
import com.abin.Footwear.E_com.model.Brand;
import com.abin.Footwear.E_com.model.Product;
import com.abin.Footwear.E_com.model.Size;
import com.abin.Footwear.E_com.repository.BrandRepository;
import com.abin.Footwear.E_com.repository.ProductRepository;
import com.abin.Footwear.E_com.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller("adminProductController")
@RequestMapping("/admin/product")
public class ProductController {

    @Autowired
    SizeRepository sizeRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    BrandRepository brandRepository;


    @PostMapping("/add")
    public String addProduct(@ModelAttribute AddProductRequest request,
                             @RequestParam("image") MultipartFile imageFile) {

        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        List<Size> sizes = sizeRepository.findAllById(request.getSizeIds());

        String imageName = imageFile.getOriginalFilename();
        try {
            String uploadDir = System.getProperty("user.dir") + "/images/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath); // create /images if it doesn't exist
            }

            Path filePath = uploadPath.resolve(imageName);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("✅ Image saved to: " + filePath);

        } catch (IOException e) {
            throw new RuntimeException("❌ Failed to store image file", e);
        }

        // ✅ Save only image name (not full path) to DB
        Product product = new Product();
        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setStatus("Active");
        product.setBrand(brand);
        product.setSizes(sizes);
        product.setImage(imageFile.getOriginalFilename());


        productRepository.save(product);

        return "redirect:/admin/products";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id,
                                @ModelAttribute AddProductRequest request,
                                @RequestParam("image") MultipartFile imageFile) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        List<Size> sizes = sizeRepository.findAllById(request.getSizeIds());

        // Update values
        existingProduct.setTitle(request.getTitle());
        existingProduct.setBrand(brand);
        existingProduct.setDescription(request.getDescription());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setCategory(request.getCategory());
        existingProduct.setSizes(sizes);

        if (!imageFile.isEmpty()) {
            String imageName = imageFile.getOriginalFilename();
            try {
                String uploadDir = System.getProperty("user.dir") + "/images/";
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Files.copy(imageFile.getInputStream(), uploadPath.resolve(imageName), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException("Failed to store image file", e);
            }
            existingProduct.setImage(imageName);
        }

        productRepository.save(existingProduct);

        return "redirect:/admin/products";

    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        productRepository.delete(product);

        return "redirect:/admin/products";
    }

    @GetMapping("/hide/{id}")
    public String hideProduct(@PathVariable Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setStatus("Hidden");
        productRepository.save(product);

        return "redirect:/admin/products"; // update as per your route
    }

    @GetMapping("/unhide/{id}")
    public String unhideProduct(@PathVariable Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setStatus("Active");
        productRepository.save(product);

        return "redirect:/admin/products";
    }



}
