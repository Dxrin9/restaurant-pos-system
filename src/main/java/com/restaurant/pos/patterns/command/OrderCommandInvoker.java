package com.restaurant.pos.patterns.command;

import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;

/** DESIGN PATTERN: Command - invoker that executes and undoes commands */
@Component
public class OrderCommandInvoker {

    private final Deque<OrderCommand> history = new ArrayDeque<>();

    public void executeCommand(OrderCommand command) {
        command.execute();
        history.push(command);
    }

    public void undoLast() {
        if (!history.isEmpty()) {
            history.pop().undo();
        }
    }
}
