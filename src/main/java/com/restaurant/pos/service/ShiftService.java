package com.restaurant.pos.service;

import com.restaurant.pos.entity.Shift;
import com.restaurant.pos.entity.User;
import com.restaurant.pos.repository.ShiftRepository;
import com.restaurant.pos.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ShiftService {

    private final ShiftRepository shiftRepository;
    private final UserRepository userRepository;

    public ShiftService(ShiftRepository shiftRepository, UserRepository userRepository) {
        this.shiftRepository = shiftRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Shift clockIn(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        Shift shift = Shift.builder()
                .user(user)
                .shiftDate(LocalDate.now())
                .startTime(LocalDateTime.now())
                .build();
        return shiftRepository.save(shift);
    }

    @Transactional
    public Shift clockOut(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        Shift shift = shiftRepository.findByUserAndEndTimeIsNull(user)
                .orElseThrow(() -> new IllegalStateException("No open shift for " + username));
        shift.setEndTime(LocalDateTime.now());
        shift.setWorkedMinutes(ChronoUnit.MINUTES.between(shift.getStartTime(), shift.getEndTime()));
        return shiftRepository.save(shift);
    }

    public List<Shift> findByDate(LocalDate date) { return shiftRepository.findByShiftDate(date); }
}
