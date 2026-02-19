package com.restaurant.pos.patterns.proxy;

import com.restaurant.pos.entity.MenuItem;
import com.restaurant.pos.repository.MenuItemRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * DESIGN PATTERN: Proxy - checks user role before returning menu items.
 */
@Component
public class SecureMenuItemProxy {

    private final MenuItemRepository menuItemRepository;

    public SecureMenuItemProxy(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public List<MenuItem> getActiveMenuItems() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new SecurityException("Not authenticated");
        }
        return menuItemRepository.findByActiveTrue();
    }
}
