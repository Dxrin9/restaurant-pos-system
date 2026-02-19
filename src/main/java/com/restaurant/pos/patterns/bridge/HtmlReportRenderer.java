package com.restaurant.pos.patterns.bridge;

import org.springframework.stereotype.Component;

/** DESIGN PATTERN: Bridge - HTML report renderer implementation */
@Component
public class HtmlReportRenderer implements ReportRenderer {
    @Override
    public String render(String title, String content) {
        return "<html><body><h1>" + title + "</h1><p>" + content + "</p></body></html>";
    }
}
