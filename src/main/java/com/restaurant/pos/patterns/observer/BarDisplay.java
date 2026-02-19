package com.restaurant.pos.patterns.observer;

import com.restaurant.pos.entity.Order;
import org.springframework.stereotype.Component;

/** DESIGN PATTERN: Observer - bar display observer */
@Component
public class BarDisplay implements OrderObserver {

    @Override
    public void onOrderUpdated(Order order) {
        System.out.println("[BAR] Order " + order.getId() + " updated, status=" + order.getStatus());
    }
}
