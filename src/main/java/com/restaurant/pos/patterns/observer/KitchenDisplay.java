package com.restaurant.pos.patterns.observer;

import com.restaurant.pos.entity.Order;
import org.springframework.stereotype.Component;

/** DESIGN PATTERN: Observer - kitchen display observer */
@Component
public class KitchenDisplay implements OrderObserver {

    @Override
    public void onOrderUpdated(Order order) {
        // In a real system this would push a notification to the kitchen screen
        System.out.println("[KITCHEN] Order " + order.getId() + " updated, status=" + order.getStatus());
    }
}
