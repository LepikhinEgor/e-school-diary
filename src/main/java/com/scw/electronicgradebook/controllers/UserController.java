package com.scw.electronicgradebook.controllers;

import com.scw.electronicgradebook.domain.dto.SensitiveDataDto;
import com.scw.electronicgradebook.domain.dto.UserDto;
import com.scw.electronicgradebook.services.PhotoUploadService;
import com.scw.electronicgradebook.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final PhotoUploadService photoUploadService;

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

    @PutMapping("/user/avatar")
    public void uploadAvatarFromUrl(String url) {
        photoUploadService.uploadPhoto(url);
    }

    @GetMapping("/user/sensitive")
    public SensitiveDataDto getUserSensitiveData(@RequestParam("user-id") Long userId) {
        return userService.getSensitiveData(userId);
    }
}
