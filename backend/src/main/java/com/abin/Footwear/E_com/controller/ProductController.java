package com.abin.Footwear.E_com.controller;

import com.abin.Footwear.E_com.dto.BrandInfoDTO;
import com.abin.Footwear.E_com.dto.CategoryImageDTO;
import com.abin.Footwear.E_com.model.Brand;
import com.abin.Footwear.E_com.model.Category;
import com.abin.Footwear.E_com.model.Product;
import com.abin.Footwear.E_com.repository.BrandRepository;
import com.abin.Footwear.E_com.repository.CategoryRepository;
import com.abin.Footwear.E_com.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    BrandRepository brandRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public List<Category> getDistinctCategoriesWithImage() {

        return categoryRepository.findAll();
    }

    @GetMapping("/brands")
    public List<BrandInfoDTO> getBrandNameWithImage() {

        return brandRepository.findAllBrandInfo();
    }

    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) {
        return productRepository.findByCategoryAndStatus(category, "ACTIVE");
    }

    @GetMapping("/brand/{brand}")
    public List<Product> getProductByBrand(@PathVariable String brand) {

        Brand foundBrand = brandRepository.findByName(brand);

        if (foundBrand == null) {
            throw new RuntimeException("Brand not found");
        }

        return productRepository.findByBrandIdAndStatus(foundBrand.getId(), "ACTIVE");
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductDetails(@PathVariable Long productId) {

       Optional<Product> product = productRepository.findById(productId);

       if (product.isEmpty()) {
           return ResponseEntity.status(404).body("Product not found");
       }

       return ResponseEntity.ok(product);

    }

    @GetMapping("/new-arrivals")
    public ResponseEntity<List<Product>> getNewArrivals() {
        List<Product> recentProducts = productRepository.findTop12ByStatusOrderByIdDesc("ACTIVE");
        return ResponseEntity.ok(recentProducts);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String query,
                                                       @RequestParam(required = false) String category){

        List<Product> results;

        if (category != null && !category.isEmpty()) {
            results = productRepository.searchByTitleOrBrandAndCategoryContainingIgnoreCase(query, category);
        } else {
            results = productRepository.searchByTitleOrBrandContainingIgnoreCase(query);
        }

        return ResponseEntity.ok(results);
    }
}
