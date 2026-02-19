package com.restaurant.pos.entity;

import com.restaurant.pos.patterns.visitor.ReportVisitor;
import com.restaurant.pos.patterns.visitor.Visitable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Shift entity - tracks employee clock-in/clock-out.
 * Implements Visitable for DESIGN PATTERN: Visitor (DailyReportVisitor).
 */
@Entity
@Table(name = "shift")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shift implements Visitable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDate shiftDate;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    /** Calculated as ChronoUnit.MINUTES.between(startTime, endTime) */
    private Long workedMinutes;

    /** DESIGN PATTERN: Visitor - accept a ReportVisitor */
    @Override
    public void accept(ReportVisitor visitor) {
        visitor.visitShift(this);
    }
}