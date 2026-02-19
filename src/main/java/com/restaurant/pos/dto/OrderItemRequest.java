package com.restaurant.pos.dto;

import lombok.Data;

@Data
public class OrderItemRequest {
    private Long menuItemId;
    private int quantity;
    private String notes;
}
