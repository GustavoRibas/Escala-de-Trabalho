package com.example.escalaCidada.controller;

import com.example.escalaCidada.entity.Shift;
import com.example.escalaCidada.service.ShiftService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/shifts")
public class ShiftController {
    private final ShiftService shiftService;

    public ShiftController(ShiftService shiftService) {
        this.shiftService = shiftService;
    }

    @GetMapping("/api/shifts")
    @ResponseBody
    public List<Shift> getAllShifts() {
        return shiftService.getAllShifts();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Shift getShiftById(@PathVariable Long id) {
        return shiftService.getShiftById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shift not found with id: " + id));
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Shift updateShift(@PathVariable Long id, @RequestBody Shift updatedShift) {
        return shiftService.getShiftById(id)
                .map(shift -> {
                    shift.setName(updatedShift.getName());
                    shift.setStartTime(updatedShift.getStartTime());
                    shift.setEndTime(updatedShift.getEndTime());
                    shift.setDuration(updatedShift.getDuration());
                    return shiftService.createShift(shift);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Shift not found with id: " + id));
    }

    @GetMapping("/new")
    public String showShiftForm(Model model) {
        model.addAttribute("shift", new Shift());
        return "new-shift";
    }

    @PostMapping("/new")
    public String createShift(@ModelAttribute Shift shift, Model model) {
        try {
            // Lógica para criar o turno
            shiftService.createShift(shift);

            // Adicione o atributo "success" ao modelo
            model.addAttribute("success", true);

            // Retorne a página de cadastro
            return "new-shift";
        } catch (Exception e) {
            // Em caso de erro, você também pode adicionar um atributo "error" ao modelo
            model.addAttribute("error", true);
            return "new-shift";
        }
    }



    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteShift(@PathVariable Long id) {
        shiftService.deleteShift(id);
    }
}
