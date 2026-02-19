package com.restaurant.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyReportDTO {
    private LocalDate date;
    private long totalOrders;
    private BigDecimal totalRevenue;
    private long totalItemsSold;
    private long activeShifts;
}
