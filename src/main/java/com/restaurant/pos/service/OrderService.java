package com.restaurant.pos.service;

import com.restaurant.pos.entity.*;
import com.restaurant.pos.enums.OrderStatus;
import com.restaurant.pos.enums.PaymentMethod;
import com.restaurant.pos.enums.StationType;
import com.restaurant.pos.patterns.builder.OrderBuilder;
import com.restaurant.pos.patterns.chain.PricingHandler;
import com.restaurant.pos.patterns.chain.ValidationHandler;
import com.restaurant.pos.patterns.command.OrderCommandInvoker;
import com.restaurant.pos.patterns.command.PlaceOrderCommand;
import com.restaurant.pos.patterns.observer.OrderObserver;
import com.restaurant.pos.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final MenuItemRepository menuItemRepository;
    private final RestaurantTableRepository tableRepository;
    private final OrderCommandInvoker commandInvoker;
    private final List<OrderObserver> observers;

    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        MenuItemRepository menuItemRepository,
                        RestaurantTableRepository tableRepository,
                        OrderCommandInvoker commandInvoker,
                        List<OrderObserver> observers) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.menuItemRepository = menuItemRepository;
        this.tableRepository = tableRepository;
        this.commandInvoker = commandInvoker;
        this.observers = observers;
    }

    public List<Order> findAllActive() {
        return orderRepository.findByStatusNotIn(List.of(OrderStatus.PAID, OrderStatus.VOIDED));
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
    }

    public java.util.Optional<Order> findActiveOrderByTable(RestaurantTable table) {
        return orderRepository.findByTableAndStatusNotIn(table, List.of(OrderStatus.PAID, OrderStatus.VOIDED));
    }

    public List<OrderItem> findPendingByStation(StationType type) {
        return orderItemRepository.findByStationTypeAndReadyFalse(type);
    }

    @Transactional
    public Order createOrder(RestaurantTable table, User waiter) {
        Order order = new OrderBuilder().withTable(table).withWaiter(waiter).build();
        order = orderRepository.save(order);
        table.setStatus("OCCUPIED");
        tableRepository.save(table);
        return order;
    }

    @Transactional
    public OrderItem addItem(Order order, Long menuItemId, int quantity, String notes) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new IllegalArgumentException("MenuItem not found: " + menuItemId));
        OrderItem item = OrderItem.builder()
                .order(order)
                .menuItem(menuItem)
                .quantity(quantity)
                .unitPrice(menuItem.getPrice())
                .notes(notes)
                .stationType(menuItem.getStationType())
                .ready(false)
                .build();
        item = orderItemRepository.save(item);
        order.getItems().add(item);
        order.recalculateTotal();
        orderRepository.save(order);
        return item;
    }

    @Transactional
    public Order sendOrder(Order order) {
        // Chain of Responsibility
        var validation = new ValidationHandler();
        validation.setNext(new PricingHandler());
        validation.handle(order);

        commandInvoker.executeCommand(new PlaceOrderCommand(order, orderRepository));
        Order saved = orderRepository.save(order);
        observers.forEach(o -> o.onOrderUpdated(saved));
        return saved;
    }

    @Transactional
    public void markItemReady(Long itemId) {
        OrderItem item = orderItemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("OrderItem not found: " + itemId));
        item.setReady(true);
        orderItemRepository.save(item);

        Order order = item.getOrder();
        boolean allReady = order.getItems().stream().allMatch(OrderItem::isReady);
        if (allReady) {
            order.setStatus(OrderStatus.READY);
            orderRepository.save(order);
        } else if (order.getStatus() == OrderStatus.SENT) {
            order.setStatus(OrderStatus.PARTIALLY_READY);
            orderRepository.save(order);
        }
        observers.forEach(o -> o.onOrderUpdated(order));
    }

    @Transactional
    public Order processPayment(Long orderId, PaymentMethod method) {
        Order order = findById(orderId);
        order.setStatus(OrderStatus.PAID);
        order.setPaymentMethod(method);
        order.setPaidAt(LocalDateTime.now());
        final Order saved = orderRepository.save(order);

        RestaurantTable table = saved.getTable();
        table.setStatus("AVAILABLE");
        tableRepository.save(table);

        observers.forEach(o -> o.onOrderUpdated(saved));
        return saved;
    }

    @Transactional
    public Order voidOrder(Long orderId) {
        Order order = findById(orderId);
        order.setStatus(OrderStatus.VOIDED);
        order = orderRepository.save(order);

        RestaurantTable table = order.getTable();
        table.setStatus("AVAILABLE");
        tableRepository.save(table);
        return order;
    }

    public List<Order> findByDateRange(LocalDateTime start, LocalDateTime end) {
        return orderRepository.findByCreatedAtBetween(start, end);
    }
}
