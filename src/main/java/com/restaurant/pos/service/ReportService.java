package com.restaurant.pos.service;

import com.restaurant.pos.dto.DailyReportDTO;
import com.restaurant.pos.entity.Order;
import com.restaurant.pos.enums.OrderStatus;
import com.restaurant.pos.patterns.template.ReportTemplate;
import com.restaurant.pos.repository.OrderRepository;
import com.restaurant.pos.repository.ShiftRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService extends ReportTemplate {

    private final OrderRepository orderRepository;
    private final ShiftRepository shiftRepository;

    public ReportService(OrderRepository orderRepository, ShiftRepository shiftRepository) {
        this.orderRepository = orderRepository;
        this.shiftRepository = shiftRepository;
    }

    @Override
    protected List<Order> fetchOrders(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();
        return orderRepository.findByCreatedAtBetween(start, end)
                .stream()
                .filter(o -> o.getStatus() == OrderStatus.PAID)
                .toList();
    }

    @Override
    protected BigDecimal calculateRevenue(List<Order> orders) {
        return orders.stream().map(Order::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    protected long countItemsSold(List<Order> orders) {
        return orders.stream()
                .flatMap(o -> o.getItems().stream())
                .mapToLong(i -> i.getQuantity())
                .sum();
    }

    @Override
    protected long fetchShifts(LocalDate date) {
        return shiftRepository.findByShiftDate(date).size();
    }

    public DailyReportDTO getReport(LocalDate date) {
        return generateReport(date);
    }
}
