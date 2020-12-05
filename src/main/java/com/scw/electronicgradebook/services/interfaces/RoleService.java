package com.scw.electronicgradebook.services.interfaces;

import com.scw.electronicgradebook.domain.entities.Role;

public interface RoleService {

    void addRole(Long userId, String role);

    void removeRole(Long userId, String role);

    Role findRole(String role);
}
