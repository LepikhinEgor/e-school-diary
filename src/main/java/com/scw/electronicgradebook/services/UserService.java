package com.scw.electronicgradebook.services;

import com.scw.electronicgradebook.domain.dto.UserDto;

import java.util.Optional;

public interface UserService {

    void create(UserDto dto);

    void update(UserDto dto, Long id);

    void delete(Long id);

    Optional<UserDto> getById(Long id);
}
