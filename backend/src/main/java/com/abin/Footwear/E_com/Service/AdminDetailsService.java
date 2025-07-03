package com.abin.Footwear.E_com.Service;

import com.abin.Footwear.E_com.model.Admin;
import com.abin.Footwear.E_com.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service("adminDetailsService")
public class AdminDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("🟡 Login attempt for: " + email);

        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> {
                    System.out.println("🔴 Admin not found: " + email);
                    return new UsernameNotFoundException("Admin not found");
                });

        System.out.println("🟢 Admin found: " + admin.getEmail());
        System.out.println("🔐 Encoded password: " + admin.getPassword());
        System.out.println("🔖 Role: " + admin.getRole());

        return new User(
                admin.getEmail(),
                admin.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(admin.getRole())) // e.g., "ROLE_ADMIN"
        );
    }

}
