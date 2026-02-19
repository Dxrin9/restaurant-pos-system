package com.restaurant.pos.patterns.template;

import com.restaurant.pos.dto.DailyReportDTO;

import java.time.LocalDate;

/**
 * DESIGN PATTERN: Template Method - defines the skeleton for report generation.
 */
public abstract class ReportTemplate {

    public final DailyReportDTO generateReport(LocalDate date) {
        var orders = fetchOrders(date);
        var revenue = calculateRevenue(orders);
        var itemsSold = countItemsSold(orders);
        var shifts = fetchShifts(date);
        return buildDTO(date, orders.size(), revenue, itemsSold, shifts);
    }

    protected abstract java.util.List<com.restaurant.pos.entity.Order> fetchOrders(LocalDate date);
    protected abstract java.math.BigDecimal calculateRevenue(java.util.List<com.restaurant.pos.entity.Order> orders);
    protected abstract long countItemsSold(java.util.List<com.restaurant.pos.entity.Order> orders);
    protected abstract long fetchShifts(LocalDate date);

    protected DailyReportDTO buildDTO(LocalDate date, long totalOrders,
                                      java.math.BigDecimal totalRevenue,
                                      long totalItemsSold, long activeShifts) {
        return new DailyReportDTO(date, totalOrders, totalRevenue, totalItemsSold, activeShifts);
    }
}
