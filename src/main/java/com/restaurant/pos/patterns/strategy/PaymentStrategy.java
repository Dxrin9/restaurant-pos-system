package com.restaurant.pos.patterns.strategy;

import com.restaurant.pos.entity.Order;

/** DESIGN PATTERN: Strategy - payment strategy interface */
public interface PaymentStrategy {
    void processPayment(Order order);
    String getMethodName();
}
