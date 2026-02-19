package com.restaurant.pos.patterns.decorator;

import com.restaurant.pos.entity.OrderItem;
import java.math.BigDecimal;

/** DESIGN PATTERN: Decorator - base decorator wrapping an OrderItem */
public abstract class OrderItemDecorator {

    protected final OrderItem wrapped;

    public OrderItemDecorator(OrderItem wrapped) {
        this.wrapped = wrapped;
    }

    public String getName() { return wrapped.getMenuItem().getName(); }
    public BigDecimal getPrice() { return wrapped.getSubtotal(); }
    public String getNotes() { return wrapped.getNotes(); }
}
