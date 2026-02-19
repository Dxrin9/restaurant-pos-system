package com.restaurant.pos.patterns.observer;

import com.restaurant.pos.entity.Order;

/** DESIGN PATTERN: Observer - observer interface */
public interface OrderObserver {
    void onOrderUpdated(Order order);
}
