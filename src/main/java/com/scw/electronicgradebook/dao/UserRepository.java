package com.scw.electronicgradebook.dao;

import com.scw.electronicgradebook.domain.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void create(User user);

    void update(User user);

    void delete(User user);

    Optional<User> getById(Long id);

    Optional<User> findByLogin(String login);

    List<User> getUsersPage(Integer fromPosition, Integer size);
}
