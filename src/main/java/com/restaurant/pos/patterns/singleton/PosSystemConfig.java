package com.restaurant.pos.patterns.singleton;

import org.springframework.stereotype.Component;

/**
 * DESIGN PATTERN: Singleton - Spring @Component guarantees a single instance.
 * Holds global POS configuration values.
 */
@Component
public class PosSystemConfig {

    private static final String VERSION = "1.0.0";
    private static final String RESTAURANT_NAME = "My Restaurant POS";
    private static final int MAX_TABLES = 50;

    public String getVersion() { return VERSION; }
    public String getRestaurantName() { return RESTAURANT_NAME; }
    public int getMaxTables() { return MAX_TABLES; }
}
