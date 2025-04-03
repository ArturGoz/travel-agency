package com.epam.finaltask.controller.thymeleaf;

import com.epam.finaltask.model.HotelType;
import com.epam.finaltask.model.TourType;
import com.epam.finaltask.model.TransferType;
import com.epam.finaltask.model.VoucherStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

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
    public String adminPage(Model model) {
        model.addAttribute("tourTypes",
                Arrays.stream(TourType.values()).map(TourType::name).toArray());
        model.addAttribute("transferTypes",
                Arrays.stream(TransferType.values()).map(TransferType::name).toArray());
        model.addAttribute("hotelTypes",
                Arrays.stream(HotelType.values()).map(HotelType::name).toArray());
        model.addAttribute("statusTypes",
                Arrays.stream(VoucherStatus.values()).map(VoucherStatus::name).toArray());
        return "adminPage";
    }

    @GetMapping("/manager")
    public String managerPage(Model model) {
        model.addAttribute("statusTypes",
                Arrays.stream(VoucherStatus.values()).map(VoucherStatus::name).toArray());
        return "managerPage";
    }
}
