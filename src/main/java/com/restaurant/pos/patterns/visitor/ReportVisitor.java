package com.restaurant.pos.patterns.visitor;

import com.restaurant.pos.entity.Order;
import com.restaurant.pos.entity.MenuItem;
import com.restaurant.pos.entity.Shift;

/** DESIGN PATTERN: Visitor - visitor interface */
public interface ReportVisitor {
    void visitOrder(Order order);
    void visitMenuItem(MenuItem menuItem);
    void visitShift(Shift shift);
}
