package com.restaurant.pos.patterns.composite;

import java.math.BigDecimal;

/** DESIGN PATTERN: Composite - component interface */
public interface MenuComponent {
    String getName();
    BigDecimal getPrice();
    void display(int depth);
}
