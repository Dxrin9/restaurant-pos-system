package com.restaurant.pos.patterns.factory;

import com.restaurant.pos.patterns.observer.OrderObserver;
import com.restaurant.pos.enums.StationType;

/** DESIGN PATTERN: Abstract Factory - interface for display factories */
public interface DisplayFactory {
    OrderObserver createDisplay(StationType type);
}
