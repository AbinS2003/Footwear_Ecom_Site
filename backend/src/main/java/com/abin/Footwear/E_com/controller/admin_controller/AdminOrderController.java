package com.abin.Footwear.E_com.controller.admin_controller;

import com.abin.Footwear.E_com.model.Order;
import com.abin.Footwear.E_com.model.Product;
import com.abin.Footwear.E_com.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

    @Autowired
    OrderRepository orderRepository;


    @GetMapping("")
    public String getOrders(@RequestParam(required = false) String status,
                            @RequestParam(defaultValue = "1") int page,
                            Model model) {

        List<Order> allOrders = orderRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Order::getId).reversed())
                .collect(Collectors.toList());


        List<Order> filteredOrders = (status == null || status.isEmpty())
                ? allOrders
                : allOrders.stream()
                .filter(o -> o.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());

        int pageSize = 10;
        int totalOrders = filteredOrders.size();
        int totalPages = (int) Math.ceil((double) totalOrders / pageSize);

        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalOrders);

        List<Order> pagedOrders = filteredOrders.subList(fromIndex, toIndex);

        model.addAttribute("orders", pagedOrders);
        model.addAttribute("status", status);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("showSearch", false);

        return "order-list";
    }


    @PostMapping("/update-status")
    public String updateOrderStatus(@RequestParam Long orderId,
                                    @RequestParam String status) {

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        orderRepository.save(order);
        return "redirect:/admin/orders";

    }

    @PostMapping("/cancel")
    public String cancelOrder(@RequestParam Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus("Cancelled");
        orderRepository.save(order);
        return "redirect:/admin/orders";
    }


}
