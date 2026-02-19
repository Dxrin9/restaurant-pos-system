package com.restaurant.pos.patterns.mediator;

import com.restaurant.pos.entity.Order;
import com.restaurant.pos.patterns.observer.OrderObserver;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * DESIGN PATTERN: Mediator - coordinates communication between waiter and kitchen/bar/pizza stations.
 */
@Component
public class OrderMediator {

    private final List<OrderObserver> observers;

    public OrderMediator(List<OrderObserver> observers) {
        this.observers = observers;
    }

    public void notifyStations(Order order) {
        observers.forEach(o -> o.onOrderUpdated(order));
    }
}
