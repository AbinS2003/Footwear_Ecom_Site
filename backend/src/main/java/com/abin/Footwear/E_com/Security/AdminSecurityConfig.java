package com.abin.Footwear.E_com.Security;

import com.abin.Footwear.E_com.Service.AdminDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// AdminSecurityConfig.java
@Configuration
@Order(1)
public class AdminSecurityConfig {

    @Autowired
    private AdminDetailsService adminDetailsService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public void configureAdminAuth(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(adminDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public SecurityFilterChain adminSecurityFilter(HttpSecurity http) throws Exception {
        System.out.println("✅ Admin security config is active");
        http
                .securityMatcher("/admin/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/login", "/admin/process-login", "/images/**").permitAll()
                        .anyRequest().hasAnyAuthority("ROLE_ADMIN")
                )
                .formLogin(form -> form
                        .loginPage("/admin/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .loginProcessingUrl("/admin/process-login")
                        .defaultSuccessUrl("/admin/pannel", true)
                        .failureUrl("/admin/login?error=true")
                )
                .logout(logout -> logout
                        .logoutUrl("/admin/logout")
                        .logoutSuccessUrl("/admin/login?logout=true")
                )
                .csrf().disable(); // or enable if needed
        return http.build();
    }
}

