package com.sadat.service.general;

import com.sadat.dto.RoleRequest;
import com.sadat.dto.RoleResponse;
import com.sadat.model.Menu;
import com.sadat.model.Role;
import com.sadat.repository.MenuRepository;
import com.sadat.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public List<RoleResponse> getRoles(){

        List<RoleResponse> responses = new ArrayList<>();

        for(Role role : roleRepository.findAll()){

            RoleResponse response = RoleResponse.builder()
                    .id(role.getId())
                    .role(role.getRole().equals("ROLE_ADMIN") ? "Admin" : "User")
                    .pages(getPages(role.getMenus()))
                    .build();

            responses.add(response);
        }

        return responses;
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
    public void updateMenu(long id, RoleRequest request) {

        roleRepository.updateMenu(id, getMenus(request.getMenus()));
    }

    private Set<Menu> getMenus(Set<Long> menuSet){

        Set<Menu> menus = new HashSet<>();

        for(Long id : menuSet){
            menus.add(menuRepository.getOne(id));
        }

        return menus;
    }

    private Set<String> getPages(Set<Menu> menus){

        Set<String> pages = new HashSet<>();

        for(Menu menu : menus){

            pages.add(menu.getUserInterface());
        }

        return pages;
    }
}
