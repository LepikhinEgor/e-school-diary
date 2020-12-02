package com.scw.electronicgradebook.controllers;

import com.scw.electronicgradebook.domain.dto.ChangePasswordDto;
import com.scw.electronicgradebook.domain.dto.RegistrationDto;
import com.scw.electronicgradebook.domain.dto.ResetPasswordDto;
import com.scw.electronicgradebook.services.ChangePasswordService;
import com.scw.electronicgradebook.services.FastLoginService;
import com.scw.electronicgradebook.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final ChangePasswordService changePasswordService;

    private final FastLoginService fastLoginService;

    @GetMapping("/auth/login")
    public ModelAndView getLoginPage(@RequestParam(value = "error", required = false) String error) {
        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }
        if (error == null)
            model.setViewName("login");
        else
            model.setViewName("login_error");
        return model;
    }

    @GetMapping("/auth/registration")
    public String getRegistrationPage() {
        return "registration";
    }

    @PostMapping("/auth/registration")
    public String registerUser(@Valid @ModelAttribute RegistrationDto registrationDto) {
        userService.register(registrationDto);

        return "login";
    }

    @PostMapping("auth/change-password")
    public String changePassword(@Valid @ModelAttribute ChangePasswordDto dto) {
        changePasswordService.changePassword(dto);

        return "main";
    }

    @PostMapping("/auth/reset-password")
    public String resetPassword(@Valid @ModelAttribute ResetPasswordDto dto) {
        changePasswordService.resetPassword(dto);

        return "main";
    }

    @GetMapping("/auth/reset-password-page")
    public String getResetPasswordPage() {
        return "reset_password";
    }

    @GetMapping("/auth/change-password-page")
    public String getChangePasswordPage() {
        return "change_password";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER', 'ROLE_STUDENT')")
    @GetMapping("/main")
    public String getMainPage() {
        return "main";
    }

    @GetMapping("/auth/dev/login-as-teacher")
    public String loginAsRandomTeacher() {
        fastLoginService.loginAsTeacher();

        return "redirect:/main";
    }
}
