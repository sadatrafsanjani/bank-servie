package com.sadat.service.general;

import com.sadat.dto.RoleRequest;
import com.sadat.dto.RoleResponse;
import com.sadat.model.Role;
import java.util.List;

public interface RoleService {

    List<RoleResponse> getRoles();
    Role findRole(String role);
    Role saveRole(Role role);
    void updateMenu(long id, RoleRequest request);
}
