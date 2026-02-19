package com.restaurant.pos.patterns.facade;

import com.restaurant.pos.dto.OrderItemRequest;
import com.restaurant.pos.entity.*;
import com.restaurant.pos.enums.OrderStatus;
import com.restaurant.pos.enums.PaymentMethod;
import com.restaurant.pos.patterns.builder.OrderBuilder;
import com.restaurant.pos.patterns.chain.PricingHandler;
import com.restaurant.pos.patterns.chain.ValidationHandler;
import com.restaurant.pos.patterns.command.OrderCommandInvoker;
import com.restaurant.pos.patterns.command.PlaceOrderCommand;
import com.restaurant.pos.patterns.observer.OrderObserver;
import com.restaurant.pos.repository.MenuItemRepository;
import com.restaurant.pos.repository.OrderItemRepository;
import com.restaurant.pos.repository.OrderRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * DESIGN PATTERN: Facade - simplifies the order creation flow.
 */
@Component
public class OrderFacade {

    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderCommandInvoker commandInvoker;
    private final List<OrderObserver> observers;

    public OrderFacade(OrderRepository orderRepository,
                       MenuItemRepository menuItemRepository,
                       OrderItemRepository orderItemRepository,
                       OrderCommandInvoker commandInvoker,
                       List<OrderObserver> observers) {
        this.orderRepository = orderRepository;
        this.menuItemRepository = menuItemRepository;
        this.orderItemRepository = orderItemRepository;
        this.commandInvoker = commandInvoker;
        this.observers = observers;
    }

    @Transactional
    public Order createAndSendOrder(RestaurantTable table, User waiter,
                                    List<OrderItemRequest> itemRequests) {
        OrderBuilder builder = new OrderBuilder().withTable(table).withWaiter(waiter);
        Order order = builder.build();
        order = orderRepository.save(order);

        for (OrderItemRequest req : itemRequests) {
            MenuItem menuItem = menuItemRepository.findById(req.getMenuItemId())
                    .orElseThrow(() -> new IllegalArgumentException("MenuItem not found: " + req.getMenuItemId()));
            OrderItem oi = OrderItem.builder()
                    .order(order)
                    .menuItem(menuItem)
                    .quantity(req.getQuantity())
                    .unitPrice(menuItem.getPrice())
                    .notes(req.getNotes())
                    .stationType(menuItem.getStationType())
                    .ready(false)
                    .build();
            orderItemRepository.save(oi);
            order.getItems().add(oi);
        }

        // Chain of Responsibility
        var validation = new ValidationHandler();
        validation.setNext(new PricingHandler());
        validation.handle(order);

        // Command
        commandInvoker.executeCommand(new PlaceOrderCommand(order, orderRepository));

        // Observer
        Order finalOrder = order;
        observers.forEach(o -> o.onOrderUpdated(finalOrder));

        return orderRepository.save(order);
    }

    @Transactional
    public Order processPayment(Order order, PaymentMethod method) {
        order.setStatus(OrderStatus.PAID);
        order.setPaymentMethod(method);
        order.setPaidAt(java.time.LocalDateTime.now());
        final Order saved = orderRepository.save(order);
        observers.forEach(o -> o.onOrderUpdated(saved));
        return saved;
    }
}
