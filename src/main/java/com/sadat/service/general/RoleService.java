package com.sadat.service.general;

import com.sadat.model.Role;

public interface RoleService {

    Role findRole(String role);
    Role saveRole(Role role);
}
