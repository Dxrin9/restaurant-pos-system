package com.restaurant.pos.patterns.factory;

import com.restaurant.pos.enums.StationType;
import com.restaurant.pos.patterns.observer.BarDisplay;
import com.restaurant.pos.patterns.observer.KitchenDisplay;
import com.restaurant.pos.patterns.observer.OrderObserver;
import com.restaurant.pos.patterns.observer.PizzaDisplay;
import org.springframework.stereotype.Component;

/**
 * DESIGN PATTERN: Factory Method - creates the correct display for a station type.
 * DESIGN PATTERN: Abstract Factory - implements DisplayFactory interface.
 */
@Component
public class StationDisplayFactory implements DisplayFactory {

    @Override
    public OrderObserver createDisplay(StationType type) {
        return switch (type) {
            case KITCHEN -> new KitchenDisplay();
            case BAR     -> new BarDisplay();
            case PIZZA   -> new PizzaDisplay();
        };
    }
}
