package com.restaurant.pos.patterns.state;

import com.restaurant.pos.entity.Order;
import com.restaurant.pos.enums.OrderStatus;

public class NewOrderState implements OrderState {
    @Override
    public void handle(Order order) {
        order.setStatus(OrderStatus.SENT);
    }
    @Override public String getStateName() { return "NEW"; }
}
