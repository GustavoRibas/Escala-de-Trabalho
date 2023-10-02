package com.example.escalaCidada.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.escalaCidada.entity.Employee;
import com.example.escalaCidada.service.EmployeeService;

import java.util.List;

@Controller // Use @Controller para indicar que esta classe lida com páginas HTML
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return employees;
    }

    @GetMapping("/api/employee")
    @ResponseBody
    public List<Employee> getAllShifts() {
        return employeeService.getAllEmployees();
    }
    
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    @GetMapping("/new") // Rota para exibir o formulário HTML
    public String showEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "new-employee"; // Nome da sua página HTML de cadastro
    }

    @PostMapping("/new")
    public String createEmployee(@ModelAttribute Employee employee, RedirectAttributes redirectAttributes) {
        try {
            // Lógica para criar o Employee
            employeeService.createEmployee(employee);
            // Adicione um atributo de redirecionamento "success" para indicar sucesso
            redirectAttributes.addAttribute("success", true);
        } catch (Exception e) {
            // Adicione um atributo de redirecionamento "error" para indicar erro
            redirectAttributes.addAttribute("error", true);
        }
        // Redirecione para a página de cadastro
        return "redirect:/employees/new";
    }



    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        return employeeService.getEmployeeById(id)
                .map(employee -> {
                    employee.setName(updatedEmployee.getName());
                    employee.setPosition(updatedEmployee.getPosition());
                    employee.setEmail(updatedEmployee.getEmail());
                    employee.setPhoneNumber(updatedEmployee.getPhoneNumber());
                    return employeeService.createEmployee(employee);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }
    
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }
}
