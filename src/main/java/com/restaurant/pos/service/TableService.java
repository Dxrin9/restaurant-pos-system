package com.restaurant.pos.service;

import com.restaurant.pos.entity.RestaurantTable;
import com.restaurant.pos.repository.RestaurantTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TableService {

    private final RestaurantTableRepository tableRepository;

    public TableService(RestaurantTableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    public List<RestaurantTable> findAll() { return tableRepository.findAll(); }

    public RestaurantTable findById(Long id) {
        return tableRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Table not found: " + id));
    }

    @Transactional
    public RestaurantTable save(RestaurantTable table) { return tableRepository.save(table); }

    @Transactional
    public void delete(Long id) { tableRepository.deleteById(id); }
}
