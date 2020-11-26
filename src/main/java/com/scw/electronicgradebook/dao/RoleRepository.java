package com.scw.electronicgradebook.dao;

import com.scw.electronicgradebook.domain.entities.Role;

import java.util.Optional;

public interface RoleRepository {

    Optional<Role> getByName(String name);
}
