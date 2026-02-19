package com.restaurant.pos.repository;

import com.restaurant.pos.entity.Shift;
import com.restaurant.pos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ShiftRepository extends JpaRepository<Shift, Long> {
    List<Shift> findByShiftDate(LocalDate date);
    Optional<Shift> findByUserAndEndTimeIsNull(User user);
}
