package com.restaurant.pos.patterns.state;

import com.restaurant.pos.entity.Order;
import com.restaurant.pos.enums.OrderStatus;

public class ReadyOrderState implements OrderState {
    @Override
    public void handle(Order order) {
        order.setStatus(OrderStatus.READY);
    }
    @Override public String getStateName() { return "READY"; }
}
