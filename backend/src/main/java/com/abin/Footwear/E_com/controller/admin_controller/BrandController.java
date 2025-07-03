package com.abin.Footwear.E_com.controller.admin_controller;

import com.abin.Footwear.E_com.dto.admin_dto.AddBrandRequest;
import com.abin.Footwear.E_com.dto.admin_dto.AddProductRequest;
import com.abin.Footwear.E_com.model.Brand;
import com.abin.Footwear.E_com.model.Product;
import com.abin.Footwear.E_com.model.Size;
import com.abin.Footwear.E_com.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequestMapping("/admin/brands")
public class BrandController {

    @Autowired
    BrandRepository brandRepository;

    @GetMapping("")
    public String getAddBrandPage(Model model) {

        model.addAttribute("showSearch", false);
        return"add-brands";

    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute AddBrandRequest request,
                             @RequestParam("image") MultipartFile imageFile,
                             RedirectAttributes redirectAttributes) {

        String imageName = imageFile.getOriginalFilename();

        try {
            String uploadDir = System.getProperty("user.dir") + "/images/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(imageName);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("✅ Image saved to: " + filePath);

        } catch (IOException e) {
            throw new RuntimeException("❌ Failed to store image file", e);
        }

        Brand brand = new Brand();
        brand.setName(request.getName());
        brand.setImage(imageFile.getOriginalFilename());

        brandRepository.save(brand);

        // ✅ Add success message
        redirectAttributes.addFlashAttribute("success", "Brand added successfully!");

        return "redirect:/admin/brands";
    }




}
