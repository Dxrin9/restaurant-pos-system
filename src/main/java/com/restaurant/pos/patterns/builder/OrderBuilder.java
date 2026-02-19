package com.restaurant.pos.patterns.builder;

import com.restaurant.pos.entity.Order;
import com.restaurant.pos.entity.OrderItem;
import com.restaurant.pos.entity.RestaurantTable;
import com.restaurant.pos.entity.User;
import com.restaurant.pos.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DESIGN PATTERN: Builder - builds an Order step by step.
 */
public class OrderBuilder {

    private RestaurantTable table;
    private User waiter;
    private OrderStatus status = OrderStatus.NEW;
    private final List<OrderItem> items = new ArrayList<>();

    public OrderBuilder withTable(RestaurantTable table) {
        this.table = table;
        return this;
    }

    public OrderBuilder withWaiter(User waiter) {
        this.waiter = waiter;
        return this;
    }

    public OrderBuilder withStatus(OrderStatus status) {
        this.status = status;
        return this;
    }

    public OrderBuilder addItem(OrderItem item) {
        this.items.add(item);
        return this;
    }

    public Order build() {
        Order order = new Order();
        order.setTable(table);
        order.setWaiter(waiter);
        order.setStatus(status);
        order.setCreatedAt(LocalDateTime.now());
        order.setItems(new ArrayList<>(items));
        order.setTotal(BigDecimal.ZERO);
        return order;
    }
}
