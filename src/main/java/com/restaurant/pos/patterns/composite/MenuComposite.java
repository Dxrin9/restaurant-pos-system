package com.restaurant.pos.patterns.composite;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** DESIGN PATTERN: Composite - a menu category that contains menu items */
public class MenuComposite implements MenuComponent {

    private final String name;
    private final List<MenuComponent> children = new ArrayList<>();

    public MenuComposite(String name) { this.name = name; }

    public void add(MenuComponent component) { children.add(component); }
    public void remove(MenuComponent component) { children.remove(component); }

    @Override
    public String getName() { return name; }

    @Override
    public BigDecimal getPrice() {
        return children.stream().map(MenuComponent::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public void display(int depth) {
        System.out.println("  ".repeat(depth) + "[" + name + "]");
        children.forEach(c -> c.display(depth + 1));
    }
}
