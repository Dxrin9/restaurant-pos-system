package com.restaurant.pos.patterns.strategy;

import com.restaurant.pos.entity.Order;
import org.springframework.stereotype.Component;

/** DESIGN PATTERN: Strategy - cash payment implementation */
@Component
public class CashPaymentStrategy implements PaymentStrategy {

    @Override
    public void processPayment(Order order) {
        System.out.println("Processing CASH payment for order " + order.getId() + ", total=" + order.getTotal());
    }

    @Override
    public String getMethodName() { return "CASH"; }
}
