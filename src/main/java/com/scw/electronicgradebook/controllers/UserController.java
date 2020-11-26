package com.scw.electronicgradebook.controllers;

import com.scw.electronicgradebook.domain.dto.RegistrationDto;
import com.scw.electronicgradebook.domain.dto.UserDto;
import com.scw.electronicgradebook.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostAuthorize("returnObject.name == authentication.principal.username")
    @GetMapping("/user/{user_id}")
    public UserDto getUser(@PathVariable("user_id") Long userId) {
        return userService.getById(userId).orElse(null);
    }

    @PreAuthorize("userDto.name == authentication.principal.username")
    @PutMapping("/user/{user_id}")
    public void updateUser(@RequestBody UserDto userDto,
                           @PathVariable("user_id") Long userId) {
        userService.update(userDto, userId);
    }

    @PostMapping("/user")
    public void registerUser(@RequestBody RegistrationDto registrationDto) {
        userService.register(registrationDto);
    }
}
