package com.restaurant.pos.patterns.chain;

import com.restaurant.pos.entity.Order;

/** DESIGN PATTERN: Chain of Responsibility - (re-)calculates order pricing */
public class PricingHandler extends OrderHandler {

    @Override
    public void handle(Order order) {
        order.recalculateTotal();
        if (next != null) next.handle(order);
    }
}
