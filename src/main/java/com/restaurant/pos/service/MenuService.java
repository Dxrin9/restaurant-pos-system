package com.restaurant.pos.service;

import com.restaurant.pos.entity.MenuCategory;
import com.restaurant.pos.entity.MenuItem;
import com.restaurant.pos.enums.StationType;
import com.restaurant.pos.repository.MenuCategoryRepository;
import com.restaurant.pos.repository.MenuItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuService {

    private final MenuItemRepository menuItemRepository;
    private final MenuCategoryRepository menuCategoryRepository;

    public MenuService(MenuItemRepository menuItemRepository,
                       MenuCategoryRepository menuCategoryRepository) {
        this.menuItemRepository = menuItemRepository;
        this.menuCategoryRepository = menuCategoryRepository;
    }

    public List<MenuItem> findAllItems() { return menuItemRepository.findAll(); }
    public List<MenuItem> findActiveItems() { return menuItemRepository.findByActiveTrue(); }
    public List<MenuItem> findByStation(StationType type) {
        return menuItemRepository.findByActiveTrueAndStationType(type);
    }

    public MenuItem findItemById(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("MenuItem not found: " + id));
    }

    public List<MenuCategory> findAllCategories() { return menuCategoryRepository.findAll(); }

    @Transactional
    public MenuItem saveItem(MenuItem item) { return menuItemRepository.save(item); }

    @Transactional
    public MenuCategory saveCategory(MenuCategory category) { return menuCategoryRepository.save(category); }

    @Transactional
    public void toggleItemActive(Long id) {
        MenuItem item = findItemById(id);
        item.setActive(!item.isActive());
        menuItemRepository.save(item);
    }
}
