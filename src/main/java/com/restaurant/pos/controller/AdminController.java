package com.restaurant.pos.controller;

import com.restaurant.pos.entity.MenuCategory;
import com.restaurant.pos.entity.MenuItem;
import com.restaurant.pos.entity.RestaurantTable;
import com.restaurant.pos.entity.User;
import com.restaurant.pos.enums.Role;
import com.restaurant.pos.enums.StationType;
import com.restaurant.pos.service.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final MenuService menuService;
    private final UserService userService;
    private final TableService tableService;
    private final OrderService orderService;
    private final ReportService reportService;

    public AdminController(MenuService menuService, UserService userService,
                           TableService tableService, OrderService orderService,
                           ReportService reportService) {
        this.menuService = menuService;
        this.userService = userService;
        this.tableService = tableService;
        this.orderService = orderService;
        this.reportService = reportService;
    }

    // ----- Dashboard -----

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalOrdersToday",
                orderService.findByDateRange(LocalDate.now().atStartOfDay(),
                        LocalDate.now().plusDays(1).atStartOfDay()).size());
        model.addAttribute("activeTables",
                tableService.findAll().stream().filter(t -> "OCCUPIED".equals(t.getStatus())).count());
        model.addAttribute("recentOrders", orderService.findAllActive());
        return "admin/dashboard";
    }

    // ----- Menu -----

    @GetMapping("/menu")
    public String menuPage(Model model) {
        model.addAttribute("items", menuService.findAllItems());
        model.addAttribute("categories", menuService.findAllCategories());
        model.addAttribute("stationTypes", StationType.values());
        model.addAttribute("newItem", new MenuItem());
        model.addAttribute("newCategory", new MenuCategory());
        return "admin/menu";
    }

    @PostMapping("/menu/item/save")
    public String saveItem(@ModelAttribute MenuItem item,
                           @RequestParam Long categoryId) {
        MenuCategory category = menuService.findAllCategories().stream()
                .filter(c -> c.getId().equals(categoryId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        item.setCategory(category);
        menuService.saveItem(item);
        return "redirect:/admin/menu";
    }

    @PostMapping("/menu/item/{id}/toggle")
    public String toggleItem(@PathVariable Long id) {
        menuService.toggleItemActive(id);
        return "redirect:/admin/menu";
    }

    @PostMapping("/menu/category/save")
    public String saveCategory(@ModelAttribute MenuCategory category) {
        menuService.saveCategory(category);
        return "redirect:/admin/menu";
    }

    // ----- Users -----

    @GetMapping("/users")
    public String usersPage(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("roles", Role.values());
        model.addAttribute("newUser", new User());
        return "admin/users";
    }

    @PostMapping("/users/save")
    public String saveUser(@ModelAttribute User user) {
        userService.save(user);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}/toggle")
    public String toggleUser(@PathVariable Long id) {
        userService.toggleActive(id);
        return "redirect:/admin/users";
    }

    // ----- Tables -----

    @GetMapping("/tables")
    public String tablesPage(Model model) {
        model.addAttribute("tables", tableService.findAll());
        model.addAttribute("newTable", new RestaurantTable());
        return "admin/tables";
    }

    @PostMapping("/tables/save")
    public String saveTable(@ModelAttribute RestaurantTable table) {
        if (table.getStatus() == null || table.getStatus().isBlank()) {
            table.setStatus("AVAILABLE");
        }
        tableService.save(table);
        return "redirect:/admin/tables";
    }

    @PostMapping("/tables/{id}/delete")
    public String deleteTable(@PathVariable Long id) {
        tableService.delete(id);
        return "redirect:/admin/tables";
    }

    // ----- Reports -----

    @GetMapping("/reports")
    public String reportsPage(@RequestParam(required = false)
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                              Model model) {
        if (date == null) date = LocalDate.now();
        model.addAttribute("report", reportService.getReport(date));
        model.addAttribute("date", date);
        model.addAttribute("orders", orderService.findByDateRange(
                date.atStartOfDay(), date.plusDays(1).atStartOfDay()));
        return "admin/reports";
    }
}
