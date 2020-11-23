package com.scw.electronicgradebook.dao;

import com.scw.electronicgradebook.domain.entities.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    void create(User user);

    void update(User user);

    void delete(User user);

    Optional<User> getById(Long id);
}
