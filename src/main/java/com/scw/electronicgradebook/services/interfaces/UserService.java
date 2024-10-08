package com.scw.electronicgradebook.services.interfaces;

import com.scw.electronicgradebook.domain.dto.RegistrationDto;
import com.scw.electronicgradebook.domain.dto.SensitiveDataDto;
import com.scw.electronicgradebook.domain.dto.UserDto;

import java.util.List;

public interface UserService {

    void register(RegistrationDto dto);

    void update(UserDto dto, Long id);

    void delete(Long id);

    UserDto getById(Long id);

    List<UserDto> getUsersPage(Integer page, Integer size);

    SensitiveDataDto getSensitiveData(Long id);
}
