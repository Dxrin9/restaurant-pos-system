package com.restaurant.pos.patterns.visitor;

/** DESIGN PATTERN: Visitor - visitable interface */
public interface Visitable {
    void accept(ReportVisitor visitor);
}
