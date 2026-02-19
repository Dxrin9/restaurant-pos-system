package com.restaurant.pos.patterns.command;

/** DESIGN PATTERN: Command - command interface */
public interface OrderCommand {
    void execute();
    void undo();
}
