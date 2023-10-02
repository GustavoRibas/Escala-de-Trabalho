package com.example.escalaCidada.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/new-employee")
    public String newemployee() {
        return "new-employee.html";
    }
    
    @GetMapping("/new-shift")
    public String newshift() {
        return "new-shift.html";
    }
    
    @GetMapping("/new-workschedule")
    public String newworkschedule() {
        return "new-workschedule.html";
    }
    
    @GetMapping("/workscheduleList")
    public String workscheduleList() {
        return "workscheduleList.html";
    }
    
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard.html";
    }
    
    @GetMapping("/edit-workschedule")
    public String editworkschedule() {
        return "edit-workschedule.html";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login.html";
    }
    
    @GetMapping("/homepage")
    public String homepage() {
        return "homepage.html";
    }
    
}
