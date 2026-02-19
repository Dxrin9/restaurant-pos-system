package com.restaurant.pos.controller;

import com.restaurant.pos.enums.StationType;
import com.restaurant.pos.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bar")
public class BarController {

    private final OrderService orderService;

    public BarController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/display")
    public String display(Model model) {
        model.addAttribute("items", orderService.findPendingByStation(StationType.BAR));
        return "bar/display";
    }

    @PostMapping("/item/{itemId}/ready")
    public String markReady(@PathVariable Long itemId) {
        orderService.markItemReady(itemId);
        return "redirect:/bar/display";
    }
}
