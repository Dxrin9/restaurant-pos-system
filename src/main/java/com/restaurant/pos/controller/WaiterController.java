package com.restaurant.pos.controller;

import com.restaurant.pos.entity.Order;
import com.restaurant.pos.entity.RestaurantTable;
import com.restaurant.pos.enums.OrderStatus;
import com.restaurant.pos.enums.PaymentMethod;
import com.restaurant.pos.repository.UserRepository;
import com.restaurant.pos.service.MenuService;
import com.restaurant.pos.service.OrderService;
import com.restaurant.pos.service.TableService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/waiter")
public class WaiterController {

    private final TableService tableService;
    private final OrderService orderService;
    private final MenuService menuService;
    private final UserRepository userRepository;

    public WaiterController(TableService tableService, OrderService orderService,
                             MenuService menuService, UserRepository userRepository) {
        this.tableService = tableService;
        this.orderService = orderService;
        this.menuService = menuService;
        this.userRepository = userRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("tables", tableService.findAll());
        return "waiter/dashboard";
    }

    @GetMapping("/table/{tableId}")
    public String tableView(@PathVariable Long tableId, Model model, Authentication auth) {
        RestaurantTable table = tableService.findById(tableId);
        Optional<Order> activeOrder = orderService.findActiveOrderByTable(table);

        if (activeOrder.isPresent()) {
            return "redirect:/waiter/order/" + activeOrder.get().getId();
        }
        // Create a new order
        var user = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new IllegalStateException("User not found"));
        Order order = orderService.createOrder(table, user);
        return "redirect:/waiter/order/" + order.getId();
    }

    @GetMapping("/order/{orderId}")
    public String orderDetail(@PathVariable Long orderId, Model model) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
        model.addAttribute("categories", menuService.findAllCategories());
        model.addAttribute("menuItems", menuService.findActiveItems());
        model.addAttribute("paymentMethods", PaymentMethod.values());
        return "waiter/order-detail";
    }

    @PostMapping("/order/{orderId}/add-item")
    public String addItem(@PathVariable Long orderId,
                          @RequestParam Long menuItemId,
                          @RequestParam int quantity,
                          @RequestParam(required = false, defaultValue = "") String notes) {
        Order order = orderService.findById(orderId);
        orderService.addItem(order, menuItemId, quantity, notes);
        return "redirect:/waiter/order/" + orderId;
    }

    @PostMapping("/order/{orderId}/send")
    public String sendOrder(@PathVariable Long orderId) {
        Order order = orderService.findById(orderId);
        if (order.getStatus() == OrderStatus.NEW) {
            orderService.sendOrder(order);
        }
        return "redirect:/waiter/order/" + orderId;
    }

    @PostMapping("/order/{orderId}/pay")
    public String processPayment(@PathVariable Long orderId,
                                 @RequestParam PaymentMethod paymentMethod) {
        orderService.processPayment(orderId, paymentMethod);
        return "redirect:/waiter/dashboard";
    }

    @PostMapping("/order/{orderId}/void")
    public String voidOrder(@PathVariable Long orderId) {
        orderService.voidOrder(orderId);
        return "redirect:/waiter/dashboard";
    }
}
