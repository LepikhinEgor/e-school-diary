package com.scw.electronicgradebook.controllers;

import com.scw.electronicgradebook.domain.dto.UserDto;
import com.scw.electronicgradebook.domain.enums.SecurityRole;
import com.scw.electronicgradebook.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/{user_id}")
    @PostAuthorize("returnObject.name == authentication.principal.username")
    public UserDto getUser(@PathVariable("user_id") Long userId) {
        return userService.getById(userId);
    }

    @PutMapping("/user/{user_id}")
    @PreAuthorize("userDto.name == authentication.principal.username")
    public void updateUser(@RequestBody UserDto userDto,
                           @PathVariable("user_id") Long userId) {
        userService.update(userDto, userId);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/users")
    public List<UserDto> getUsersPage(@RequestParam("page") Integer page,
                                      @RequestParam("size") Integer size) {
        return userService.getUsersPage(page, size);
    }
}
