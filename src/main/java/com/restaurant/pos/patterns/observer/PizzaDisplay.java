package com.restaurant.pos.patterns.observer;

import com.restaurant.pos.entity.Order;
import org.springframework.stereotype.Component;

/** DESIGN PATTERN: Observer - pizza display observer */
@Component
public class PizzaDisplay implements OrderObserver {

    @Override
    public void onOrderUpdated(Order order) {
        System.out.println("[PIZZA] Order " + order.getId() + " updated, status=" + order.getStatus());
    }
}
