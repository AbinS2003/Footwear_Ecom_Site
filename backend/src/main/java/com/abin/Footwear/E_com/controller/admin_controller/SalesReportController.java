package com.abin.Footwear.E_com.controller.admin_controller;

import com.abin.Footwear.E_com.model.Order;
import com.abin.Footwear.E_com.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class SalesReportController {

    @Autowired
    OrderRepository orderRepository;

    @GetMapping("/sales-report")
    public String getSalesReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String status,
            Model model) {

        List<Order> orders = orderRepository.findAll();

        if (startDate != null && endDate != null) {
            orders = orders.stream()
                    .filter(o -> !o.getDate().isBefore(startDate) && !o.getDate().isAfter(endDate))
                    .collect(Collectors.toList());
        }

        if (status != null && !status.isEmpty() && !status.equalsIgnoreCase("All")) {
            orders = orders.stream()
                    .filter(o -> o.getStatus().equalsIgnoreCase(status))
                    .collect(Collectors.toList());
        }

        int totalOrders = orders.size();
        double totalRevenue = orders.stream()
                .filter(o -> !o.getStatus().equalsIgnoreCase("Cancelled") && !o.getStatus().equalsIgnoreCase("Pending"))
                .mapToDouble(o -> o.getProduct().getPrice() * o.getQuantity())
                .sum();

        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("orders", orders);
        model.addAttribute("selectedStatus", status); // pass selected status
        model.addAttribute("showSearch", false);

        return "sales-report";
    }


}
