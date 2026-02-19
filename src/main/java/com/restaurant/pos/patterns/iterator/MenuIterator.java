package com.restaurant.pos.patterns.iterator;

import com.restaurant.pos.entity.MenuItem;

import java.util.Iterator;
import java.util.List;

/** DESIGN PATTERN: Iterator - iterates over a list of menu items */
public class MenuIterator implements Iterator<MenuItem> {

    private final List<MenuItem> items;
    private int index = 0;

    public MenuIterator(List<MenuItem> items) { this.items = items; }

    @Override
    public boolean hasNext() { return index < items.size(); }

    @Override
    public MenuItem next() {
        if (!hasNext()) throw new java.util.NoSuchElementException();
        return items.get(index++);
    }
}
