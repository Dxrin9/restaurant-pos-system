package com.restaurant.pos.patterns.bridge;

/**
 * DESIGN PATTERN: Bridge - implementation interface for report rendering.
 */
public interface ReportRenderer {
    String render(String title, String content);
}
