package com.scw.electronicgradebook.controllers;

import com.scw.electronicgradebook.domain.dto.RegistrationDto;
import com.scw.electronicgradebook.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/auth/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/auth/registration")
    public String getRegistrationPage() {
        return "registration";
    }

    @PostMapping("/auth/registration")
    public String registerUser(@ModelAttribute RegistrationDto registrationDto) {
        userService.register(registrationDto);

        return "login";
    }
}
