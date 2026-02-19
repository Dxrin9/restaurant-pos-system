package com.restaurant.pos.entity;

import com.restaurant.pos.enums.StationType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * DESIGN PATTERN: Prototype - implements clone() to copy menu items.
 * DESIGN PATTERN: Flyweight - cached by ID in MenuItemFlyweightFactory.
 */
@Entity
@Table(name = "menu_item")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MenuItem implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StationType stationType;

    @Column(nullable = false)
    private boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private MenuCategory category;

    /** DESIGN PATTERN: Prototype */
    @Override
    public MenuItem clone() {
        try {
            return (MenuItem) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone not supported", e);
        }
    }
}
