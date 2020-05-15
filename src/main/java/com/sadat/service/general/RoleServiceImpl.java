package com.sadat.service.general;

import com.sadat.dto.RoleRequest;
import com.sadat.model.Menu;
import com.sadat.model.Role;
import com.sadat.repository.MenuRepository;
import com.sadat.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;
    private MenuRepository menuRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository,
                           MenuRepository menuRepository) {
        this.roleRepository = roleRepository;
        this.menuRepository = menuRepository;
    }

    @Override
    public Role findRole(String role){

        return roleRepository.findByRole(role);
    }

    @Override
    public Role saveRole(Role role){

        if(findRole(role.getRole()) == null){

            return roleRepository.save(role);
        }

        return null;
    }

    @Override
    public void updateRoleMenu(long id, RoleRequest request) {

        Menu menu = menuRepository.getOne(request.getMenuId());
    }

}
