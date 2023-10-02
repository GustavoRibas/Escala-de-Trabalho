package com.example.escalaCidada.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.escalaCidada.entity.Shift;

public interface ShiftRepository extends JpaRepository<Shift, Long> {

    @Query(value = "SELECT s.* FROM shifts s " +
                   "JOIN work_schedules ws ON s.id = ws.shift_id " +
                   "GROUP BY s.id " +
                   "ORDER BY COUNT(ws.id) DESC " +
                   "LIMIT 1", nativeQuery = true)
    Shift findShiftWithMostSchedules();
}
