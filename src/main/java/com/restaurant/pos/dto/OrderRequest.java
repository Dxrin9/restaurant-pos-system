package com.restaurant.pos.dto;

import com.restaurant.pos.enums.PaymentMethod;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private Long tableId;
    private List<OrderItemRequest> items;
    private PaymentMethod paymentMethod;
}
