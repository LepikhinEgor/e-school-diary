package com.scw.electronicgradebook.controllers;

import com.scw.electronicgradebook.domain.dto.UserDto;
import com.scw.electronicgradebook.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @Secured("ROLE_ADMIN")
    @PostMapping("/user/role")
    public void addRole(@RequestParam("user-id") Long userId,
                        @RequestParam("role") String role) {
        userService.addRole(userId, role);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/users")
    public List<UserDto> getUsersPage(@RequestParam("page") Integer page,
                                      @RequestParam("size") Integer size) {
        return userService.getUsersPage(page, size);
    }

}
