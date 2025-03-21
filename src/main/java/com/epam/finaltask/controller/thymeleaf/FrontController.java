package com.epam.finaltask.controller.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class FrontController {
    @GetMapping("/login")
    public String loginPage() {
        return "loginPage";
    }
    @GetMapping("/register")
    public String registerPage() {
        return "registerPage";
    }
    @GetMapping("/main")
    public String mainPage() {
        return "mainPage";
    }
    @GetMapping("/admin")
    public String adminPage() {
        return "adminPage";
    }
    @GetMapping("/manager")
    public String managerPage() {
        return "managerPage";
    }
}
