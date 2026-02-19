package com.restaurant.pos.patterns.decorator;

import com.restaurant.pos.entity.OrderItem;

/** DESIGN PATTERN: Decorator - adds extra notes to an order item */
public class NoteDecorator extends OrderItemDecorator {

    private final String extraNote;

    public NoteDecorator(OrderItem wrapped, String extraNote) {
        super(wrapped);
        this.extraNote = extraNote;
    }

    @Override
    public String getNotes() {
        String existing = super.getNotes();
        if (existing == null || existing.isBlank()) return extraNote;
        return existing + " | " + extraNote;
    }
}
