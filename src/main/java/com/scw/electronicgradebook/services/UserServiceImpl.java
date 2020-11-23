package com.scw.electronicgradebook.services;

import com.scw.electronicgradebook.dao.UserRepository;
import com.scw.electronicgradebook.domain.dto.UserDto;
import com.scw.electronicgradebook.domain.entities.User;
import com.scw.electronicgradebook.domain.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private UserMapper userMapper;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void create(UserDto dto) {
        User user = userMapper.toEntity(dto,null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.create(user);
    }

    @Override
    @Transactional
    public void update(UserDto dto, Long id) {
        User user = userMapper.toEntity(dto,id);

        userRepository.update(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<User> foundUser = userRepository.getById(id);

        foundUser.ifPresent(user -> userRepository.delete(user));
    }

    @Override
    @Transactional
    public Optional<UserDto> getById(Long id) {
        Optional<User> foundUser = userRepository.getById(id);

        return foundUser.map(user -> userMapper.toDto(user));
    }
}
