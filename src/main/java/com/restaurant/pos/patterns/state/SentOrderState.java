package com.restaurant.pos.patterns.state;

import com.restaurant.pos.entity.Order;
import com.restaurant.pos.enums.OrderStatus;

public class SentOrderState implements OrderState {
    @Override
    public void handle(Order order) {
        order.setStatus(OrderStatus.PARTIALLY_READY);
    }
    @Override public String getStateName() { return "SENT"; }
}
