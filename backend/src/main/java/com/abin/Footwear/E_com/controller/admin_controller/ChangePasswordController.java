package com.abin.Footwear.E_com.controller.admin_controller;

import com.abin.Footwear.E_com.dto.admin_dto.ChangePasswordRequest;
import com.abin.Footwear.E_com.model.Admin;
import com.abin.Footwear.E_com.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class ChangePasswordController {

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/change-password")
    public String changePassword(Model model) {
        model.addAttribute("changePasswordRequest", new ChangePasswordRequest());
        model.addAttribute("showSearch", false);
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@ModelAttribute ChangePasswordRequest request,
                                 Principal principal,
                                 Model model) {
        String email = principal.getName();
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));

        // Validate old password
        if (!passwordEncoder.matches(request.getOldPassword(), admin.getPassword())) {
            model.addAttribute("error", "Old password is incorrect");
            return "change-password";
        }

        admin.setPassword(passwordEncoder.encode(request.getNewPassword()));
        adminRepository.save(admin);

        model.addAttribute("success", "Password changed successfully");
        return "change-password";
    }

}
