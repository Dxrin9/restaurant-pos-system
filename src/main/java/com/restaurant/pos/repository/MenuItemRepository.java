package com.restaurant.pos.repository;

import com.restaurant.pos.entity.MenuItem;
import com.restaurant.pos.enums.StationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByActiveTrue();
    List<MenuItem> findByActiveTrueAndStationType(StationType stationType);
}
