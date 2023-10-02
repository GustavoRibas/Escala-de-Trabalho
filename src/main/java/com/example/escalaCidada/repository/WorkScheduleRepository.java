package com.example.escalaCidada.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.escalaCidada.entity.Employee;
import com.example.escalaCidada.entity.Shift;
import com.example.escalaCidada.entity.WorkSchedule;

import java.time.LocalDate;
import java.util.List;

public interface WorkScheduleRepository extends JpaRepository<WorkSchedule, Long> {

    List<WorkSchedule> findByEmployeeAndDate(Employee employee, LocalDate date);

    @Query(value = "SELECT date " +  // Modificação aqui: selecione apenas a data do dia com mais escalas
            "FROM work_schedules " +
            "GROUP BY date " +
            "ORDER BY COUNT(id) DESC " +
            "LIMIT 1", nativeQuery = true)
    		String findDateWithMostSchedules(); // Alteração no tipo de retorno: agora é uma String para a data do dia com mais escalas

    @Query(value = "SELECT s.* FROM shifts s " +
                   "JOIN work_schedules ws ON s.id = ws.shift_id " +
                   "GROUP BY s.id " +
                   "ORDER BY COUNT(ws.id) DESC " +
                   "LIMIT 1", nativeQuery = true)
    Shift findShiftWithMostSchedules();

    @Query(value = "SELECT e.name " +
            "FROM employees e " +
            "JOIN work_schedules ws ON e.id = ws.employee_id " +
            "GROUP BY e.id " +
            "ORDER BY COUNT(ws.id) DESC " +
            "LIMIT 1", nativeQuery = true)
List<Object[]> findEmployeeWithMostSchedules();
}
