package com.example.escalaCidada.controller;

import org.springframework.web.bind.annotation.*;
import com.example.escalaCidada.entity.Employee;
import com.example.escalaCidada.entity.WorkSchedule;
import com.example.escalaCidada.entity.Shift; 
import com.example.escalaCidada.service.EmployeeService;
import com.example.escalaCidada.service.WorkScheduleService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import com.example.escalaCidada.service.ShiftService; 

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/work-schedules")
public class WorkScheduleController {
    private final WorkScheduleService workScheduleService;
    private final EmployeeService employeeService;
    private final ShiftService shiftService; 

    public WorkScheduleController(WorkScheduleService workScheduleService, EmployeeService employeeService, ShiftService shiftService) {
        this.workScheduleService = workScheduleService;
        this.employeeService = employeeService;
        this.shiftService = shiftService; 
    }

    @GetMapping("/api/workschedules")
    @ResponseBody
    public List<WorkSchedule> getAllWorkSchedules() {
        return workScheduleService.getAllWorkSchedules();
    }
    
    @GetMapping("/new")
    public String showWorkScheduleForm(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        List<Shift> shifts = shiftService.getAllShifts();
        
        model.addAttribute("employees", employees);
        model.addAttribute("shifts", shifts);
        
        return "new-workschedule";
    }


    @PostMapping("/create")
    @ResponseBody // Para indicar que o método retorna uma resposta JSON
    public ResponseEntity<String> createWorkSchedule(
            @RequestParam Long employeeId,
            @RequestParam Long shiftId,
            @RequestParam String date) {
        try {
            // Busque os objetos Employee e Shift com base nos IDs fornecidos
            Employee employee = employeeService.getEmployeeById(employeeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
            Shift shift = shiftService.getShiftById(shiftId)
                    .orElseThrow(() -> new ResourceNotFoundException("Shift not found with id: " + shiftId));

            // Crie um novo objeto WorkSchedule com os objetos Employee, Shift e data
            WorkSchedule workSchedule = new WorkSchedule();
            workSchedule.setEmployee(employee);
            workSchedule.setShift(shift);
            workSchedule.setDate(LocalDate.parse(date));

            // Lógica para criar o Work Schedule
            workScheduleService.createWorkSchedule(workSchedule);
            return new ResponseEntity<>("Cadastro realizado com sucesso!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao cadastrar: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/edit-workschedule/{id}")
    public String editWorkSchedule(@PathVariable Long id, Model model) {
        // Lógica para carregar os dados da Work Schedule com o ID fornecido e enviá-los para a página
        WorkSchedule workSchedule = workScheduleService.getWorkScheduleById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Work Schedule not found with id: " + id));
        model.addAttribute("workSchedule", workSchedule);
        return "edit-workschedule";
    }


    
    @GetMapping("/employee/{employeeId}")
    public List<WorkSchedule> getWorkSchedulesByEmployeeAndDate(
            @PathVariable Long employeeId,
            @RequestParam String date) {
        Employee employee = employeeService.getEmployeeById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
        return workScheduleService.getWorkSchedulesByEmployeeAndDate(employee, LocalDate.parse(date));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateWorkSchedule(@PathVariable Long id, @RequestBody WorkSchedule updatedWorkSchedule) {
        try {
            Optional<WorkSchedule> existingWorkSchedule = workScheduleService.getWorkScheduleById(id);

            if (existingWorkSchedule.isPresent()) {
                WorkSchedule workSchedule = existingWorkSchedule.get();
                workSchedule.setEmployee(updatedWorkSchedule.getEmployee());

                // Aqui, você pode adicionar código para lidar com o turno de trabalho (Shift)
                Shift shift = shiftService.getShiftById(updatedWorkSchedule.getShift().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Shift not found with id: " + updatedWorkSchedule.getShift().getId()));
                workSchedule.setShift(shift);

                workSchedule.setDate(updatedWorkSchedule.getDate());
                workScheduleService.updateWorkSchedule(workSchedule); // Implemente essa função no seu serviço

                return new ResponseEntity<>("Work Schedule atualizada com sucesso!", HttpStatus.OK);
            } else {
                throw new ResourceNotFoundException("Work Schedule not found with id: " + id);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao atualizar Work Schedule: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/api/workschedules/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteWorkSchedule(@PathVariable Long id) {
        try {
            workScheduleService.deleteWorkSchedule(id);
            return new ResponseEntity<>("Work Schedule removida com sucesso!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao remover Work Schedule: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
}

