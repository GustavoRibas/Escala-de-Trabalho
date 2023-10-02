package com.example.escalaCidada.service;

import org.springframework.stereotype.Service;
import com.example.escalaCidada.entity.Employee;
import com.example.escalaCidada.entity.WorkSchedule;
import com.example.escalaCidada.repository.WorkScheduleRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class WorkScheduleService {
    private final WorkScheduleRepository workScheduleRepository;

    public WorkScheduleService(WorkScheduleRepository workScheduleRepository) {
        this.workScheduleRepository = workScheduleRepository;
    }

    public List<WorkSchedule> getAllWorkSchedules() {
        return workScheduleRepository.findAll();
    }

    public List<WorkSchedule> getWorkSchedulesByEmployeeAndDate(Employee employee, LocalDate date) {
        return workScheduleRepository.findByEmployeeAndDate(employee, date);
    }

    public WorkSchedule createWorkSchedule(WorkSchedule workSchedule) {
        return workScheduleRepository.save(workSchedule);
    }

    public Optional<WorkSchedule> getWorkScheduleById(Long id) {
        return workScheduleRepository.findById(id);
    }

    public void deleteWorkSchedule(Long id) {
        workScheduleRepository.deleteById(id);
    }

    public WorkSchedule updateWorkSchedule(WorkSchedule workSchedule) {
        return workScheduleRepository.save(workSchedule);
    }
}
