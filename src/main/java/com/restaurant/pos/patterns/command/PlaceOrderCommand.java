package com.restaurant.pos.patterns.command;

import com.restaurant.pos.entity.Order;
import com.restaurant.pos.enums.OrderStatus;
import com.restaurant.pos.repository.OrderRepository;

/** DESIGN PATTERN: Command - place (send) an order */
public class PlaceOrderCommand implements OrderCommand {

    private final Order order;
    private final OrderRepository orderRepository;
    private OrderStatus previousStatus;

    public PlaceOrderCommand(Order order, OrderRepository orderRepository) {
        this.order = order;
        this.orderRepository = orderRepository;
    }

    @Override
    public void execute() {
        previousStatus = order.getStatus();
        order.setStatus(OrderStatus.SENT);
        orderRepository.save(order);
    }

    @Override
    public void undo() {
        order.setStatus(previousStatus);
        orderRepository.save(order);
    }
}
