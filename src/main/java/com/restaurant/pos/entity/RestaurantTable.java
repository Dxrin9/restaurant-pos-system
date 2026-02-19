package com.restaurant.pos.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "restaurant_table")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String tableNumber;

    @Column(nullable = false)
    private String status = "AVAILABLE";
}
