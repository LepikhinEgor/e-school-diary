package com.scw.electronicgradebook.domain.mappers;

import com.scw.electronicgradebook.dao.UserRepository;
import com.scw.electronicgradebook.domain.dto.RegistrationDto;
import com.scw.electronicgradebook.domain.dto.UserDto;
import com.scw.electronicgradebook.domain.entities.User;
import com.scw.electronicgradebook.domain.enums.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final UserRepository userRepository;

    public User toEntity(RegistrationDto dto, Long id) {
        User user = id != null ? findUser(id) : new User();

        user.setLogin(dto.getLogin());
        user.setName(dto.getName());
        user.setAge(dto.getAge());
        user.setPassword(dto.getPassword());
        user.setUserType(UserType.valueOf(dto.getUserType().toUpperCase()));
        user.setSecurityAnswer(dto.getSecurityAnswer());
        user.setAddress(dto.getAddress());
        user.setPhone(dto.getPhone());

        return user;
    }

    public User toEntity(UserDto dto, Long id) {
        User user = new User();
        user.setId(id);
        user.setLogin(dto.getLogin());
        user.setName(dto.getName());
        user.setAge(dto.getAge());
        user.setUserType(UserType.valueOf(dto.getUserType().toUpperCase()));

        return user;
    }


    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setLogin(user.getLogin());
        userDto.setName(user.getName());
        userDto.setAge(user.getAge());
        userDto.setUserType(user.getUserType().name());

        return userDto;
    }

    private User findUser(Long id) {
        Optional<User> student = userRepository.getById(id);
        if (student.isEmpty())
            throw new IllegalArgumentException("User with id " + id + " not found");

        return student.get();
    }
}
