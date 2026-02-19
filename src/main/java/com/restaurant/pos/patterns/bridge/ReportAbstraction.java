package com.restaurant.pos.patterns.bridge;

/**
 * DESIGN PATTERN: Bridge - abstraction that uses a ReportRenderer implementation.
 */
public abstract class ReportAbstraction {

    protected final ReportRenderer renderer;

    public ReportAbstraction(ReportRenderer renderer) {
        this.renderer = renderer;
    }

    public abstract String generate(String content);
}
