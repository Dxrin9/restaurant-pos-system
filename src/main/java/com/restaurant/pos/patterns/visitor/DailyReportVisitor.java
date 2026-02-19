package com.restaurant.pos.patterns.visitor;

import com.restaurant.pos.entity.MenuItem;
import com.restaurant.pos.entity.Order;
import com.restaurant.pos.entity.Shift;
import com.restaurant.pos.enums.OrderStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/** DESIGN PATTERN: Visitor - concrete visitor that computes daily report data */
@Component
public class DailyReportVisitor implements ReportVisitor {

    private BigDecimal totalRevenue = BigDecimal.ZERO;
    private long totalOrders = 0;
    private long totalShiftMinutes = 0;

    @Override
    public void visitOrder(Order order) {
        if (order.getStatus() == OrderStatus.PAID) {
            totalRevenue = totalRevenue.add(order.getTotal());
            totalOrders++;
        }
    }

    @Override
    public void visitMenuItem(MenuItem menuItem) {
        // no-op for daily report
    }

    @Override
    public void visitShift(Shift shift) {
        if (shift.getWorkedMinutes() != null) {
            totalShiftMinutes += shift.getWorkedMinutes();
        }
    }

    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public long getTotalOrders() { return totalOrders; }
    public long getTotalShiftMinutes() { return totalShiftMinutes; }

    public void reset() {
        totalRevenue = BigDecimal.ZERO;
        totalOrders = 0;
        totalShiftMinutes = 0;
    }
}
