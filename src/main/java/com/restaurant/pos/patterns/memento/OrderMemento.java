package com.restaurant.pos.patterns.memento;

import com.restaurant.pos.enums.OrderStatus;

/**
 * DESIGN PATTERN: Memento - saves and restores order state.
 */
public class OrderMemento {

    private final OrderStatus status;
    private final java.math.BigDecimal total;

    public OrderMemento(OrderStatus status, java.math.BigDecimal total) {
        this.status = status;
        this.total = total;
    }

    public OrderStatus getStatus() { return status; }
    public java.math.BigDecimal getTotal() { return total; }
}
