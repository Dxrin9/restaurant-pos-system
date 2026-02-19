package com.restaurant.pos.patterns.strategy;

import com.restaurant.pos.entity.Order;
import org.springframework.stereotype.Component;

/** DESIGN PATTERN: Strategy - card payment implementation */
@Component
public class CardPaymentStrategy implements PaymentStrategy {

    @Override
    public void processPayment(Order order) {
        System.out.println("Processing CARD payment for order " + order.getId() + ", total=" + order.getTotal());
    }

    @Override
    public String getMethodName() { return "CARD"; }
}
