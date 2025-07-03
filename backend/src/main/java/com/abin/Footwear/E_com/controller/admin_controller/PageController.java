package com.abin.Footwear.E_com.controller.admin_controller;


import com.abin.Footwear.E_com.dto.admin_dto.ChangePasswordRequest;
import com.abin.Footwear.E_com.model.*;
import com.abin.Footwear.E_com.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class PageController {

    @Autowired
    SizeRepository sizeRepository;
    @Autowired
    BrandRepository brandRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;


    @GetMapping("/login")
    public String showLoginPage() {
        return "admin-login"; // Thymeleaf template: admin-admin-login.html
    }

    // Redirect here after successful login
    @GetMapping("/pannel")
    public String showDashboard() {
        return "admin-pannel"; // Create this HTML page as your admin home
    }

    @GetMapping("/products")
        public String getProducts(@RequestParam(defaultValue = "1") int page, Model model) {
            List<Product> allProducts = productRepository.findAll();

            int pageSize = 10;
            int totalProducts = allProducts.size();
            int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

            int fromIndex = (page - 1) * pageSize;
            int toIndex = Math.min(fromIndex + pageSize, totalProducts);

            List<Product> products = allProducts.subList(fromIndex, toIndex);

            model.addAttribute("products", products);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("showSearch", false);
            return "product-list";
    }

    @GetMapping("/products/add")
        public String getAddProduct(Model model) {

        List<Size> allSizes = sizeRepository.findAll();
        List<Brand> brands = brandRepository.findAll();

        model.addAttribute("brands", brands);
        model.addAttribute("sizes", allSizes);
        model.addAttribute("showSearch", false);
        return "add-products";
    }


    @GetMapping("/product/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));
        model.addAttribute("product", product);
        model.addAttribute("brands", brandRepository.findAll());
        model.addAttribute("sizes", sizeRepository.findAll());
        model.addAttribute("showSearch", false); // optional, for navbar
        return "edit-product"; // this should match the name of your HTML file
    }




    @GetMapping("/users")
        public String getUsers(@RequestParam(defaultValue = "1") int page, Model model) {

        List<User> allUsers = userRepository.findAll();

        int pageSize = 10;
        int totalUsers = allUsers.size();
        int totalPages = (int)Math.ceil((double) totalUsers / pageSize);

        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalUsers);

        List<User> users = allUsers.subList(fromIndex, toIndex);

        model.addAttribute("users",users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("showSearch", false);
        return "users-list";
    }

    @GetMapping("/product/details/{id}")
    public String viewProductDetails(@PathVariable Long id, Model model) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            model.addAttribute("product", productOpt.get());
        } else {
            model.addAttribute("product", null); // fallback
        }
        model.addAttribute("showSearch", false);
        return "product-details"; // maps to product-details.html
    }

    @GetMapping("/orders/user/{userId}")
    public String viewUserOrders(@PathVariable Long userId, Model model) {

        List<Order> orders = orderRepository.findByUserId(userId);

         model.addAttribute("orders", orders);
        model.addAttribute("showSearch", false);

        if (orders == null || orders.isEmpty()) {
            model.addAttribute("noOrders", true);  // flag for no orders
        }

        return "user-orders";
    }


}
