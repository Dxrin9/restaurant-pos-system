package com.restaurant.pos.patterns.chain;

import com.restaurant.pos.entity.Order;

/** DESIGN PATTERN: Chain of Responsibility - handler interface */
public abstract class OrderHandler {

    protected OrderHandler next;

    public OrderHandler setNext(OrderHandler next) {
        this.next = next;
        return next;
    }

    public abstract void handle(Order order);
}
