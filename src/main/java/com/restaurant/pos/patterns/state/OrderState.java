package com.restaurant.pos.patterns.state;

import com.restaurant.pos.entity.Order;

/** DESIGN PATTERN: State - state interface */
public interface OrderState {
    void handle(Order order);
    String getStateName();
}
