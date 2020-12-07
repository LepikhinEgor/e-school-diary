package com.scw.electronicgradebook.controllers;

import com.scw.electronicgradebook.domain.dto.UserDto;
import com.scw.electronicgradebook.services.interfaces.RoleService;
import com.scw.electronicgradebook.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;

    @Secured("ROLE_ADMIN")
    @PostMapping("/user/role")
    public ResponseEntity<String> addRole(@RequestParam("user-id") Long userId,
                                  @RequestParam("role") String role) {
        roleService.addRole(userId, role);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/user/role")
    public void removeRole(@RequestParam("user-id") Long userId,
                           @RequestParam("role") String role) {
        roleService.removeRole(userId, role);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/users")
    public List<UserDto> getUsersPage(@RequestParam("page") Integer page,
                                      @RequestParam("size") Integer size) {
        return userService.getUsersPage(page, size);
    }

}
