package com.restaurant.pos.patterns.flyweight;

import com.restaurant.pos.entity.MenuItem;
import com.restaurant.pos.repository.MenuItemRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * DESIGN PATTERN: Flyweight - caches MenuItem objects by ID to avoid repeated DB lookups.
 */
@Component
public class MenuItemFlyweightFactory {

    private final Map<Long, MenuItem> cache = new HashMap<>();
    private final MenuItemRepository menuItemRepository;

    public MenuItemFlyweightFactory(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public MenuItem getMenuItem(Long id) {
        return cache.computeIfAbsent(id, k -> menuItemRepository.findById(k)
                .orElseThrow(() -> new IllegalArgumentException("MenuItem not found: " + k)));
    }

    public void evict(Long id) { cache.remove(id); }
    public void clear() { cache.clear(); }
}
