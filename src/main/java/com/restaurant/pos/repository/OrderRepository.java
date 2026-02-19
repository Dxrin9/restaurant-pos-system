package com.restaurant.pos.repository;

import com.restaurant.pos.entity.Order;
import com.restaurant.pos.entity.RestaurantTable;
import com.restaurant.pos.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatusNotIn(List<OrderStatus> statuses);
    Optional<Order> findByTableAndStatusNotIn(RestaurantTable table, List<OrderStatus> statuses);
    List<Order> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    List<Order> findByStatus(OrderStatus status);
}
