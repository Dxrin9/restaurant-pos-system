package com.restaurant.pos.patterns.chain;

import com.restaurant.pos.entity.Order;

/** DESIGN PATTERN: Chain of Responsibility - validates the order */
public class ValidationHandler extends OrderHandler {

    @Override
    public void handle(Order order) {
        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new IllegalStateException("Order has no items");
        }
        if (order.getTable() == null) {
            throw new IllegalStateException("Order has no table assigned");
        }
        if (next != null) next.handle(order);
    }
}
