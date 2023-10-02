package com.example.escalaCidada.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.escalaCidada.entity.Shift;
import com.example.escalaCidada.repository.EmployeeRepository;
import com.example.escalaCidada.repository.ShiftRepository;
import com.example.escalaCidada.repository.WorkScheduleRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardDataController {

    @Autowired
    private WorkScheduleRepository workScheduleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ShiftRepository shiftRepository;

    @GetMapping("/data")
    public Map<String, Object> getDashboardData() {
        Map<String, Object> dashboardData = new HashMap<>();

        // Encontre a data com mais escalas
        String dateWithMostSchedules = workScheduleRepository.findDateWithMostSchedules(); // Modificação aqui

        // Encontre o turno com mais escalas
        Shift shiftWithMostSchedules = shiftRepository.findShiftWithMostSchedules();

        // Encontre os funcionários com mais escalas
        List<Object[]> employeesWithMostSchedules = workScheduleRepository.findEmployeeWithMostSchedules();

        // Conte o número total de funcionários
        Long totalEmployees = employeeRepository.countTotalEmployees();

        // Adicione os dados ao mapa
        dashboardData.put("dateWithMostSchedules", dateWithMostSchedules);
        dashboardData.put("shiftWithMostSchedules", shiftWithMostSchedules);
        dashboardData.put("employeesWithMostSchedules", employeesWithMostSchedules);
        dashboardData.put("totalEmployees", totalEmployees);

        return dashboardData;
    }
}
