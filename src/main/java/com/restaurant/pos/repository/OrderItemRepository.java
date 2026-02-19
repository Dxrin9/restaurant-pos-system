package com.restaurant.pos.repository;

import com.restaurant.pos.entity.OrderItem;
import com.restaurant.pos.enums.StationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByStationTypeAndReadyFalse(StationType stationType);
}
