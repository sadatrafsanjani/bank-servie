package com.sadat.service.general;

import com.sadat.dto.MenuRequest;
import com.sadat.dto.MenuResponse;
import com.sadat.model.Menu;
import com.sadat.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    private MenuRepository menuRepository;

    @Autowired
    public MenuServiceImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<MenuResponse> getMenus(){

        List<MenuResponse> responses = new ArrayList<>();

        for(Menu menu : menuRepository.findAll()){

            MenuResponse response = MenuResponse.builder()
                    .id(menu.getId())
                    .userInterface(menu.getUserInterface())
                    .build();

            responses.add(response);
        }

        return responses;
    }

    @Override
    public Menu findMenu(String userInterface){

        return menuRepository.findByUserInterface(userInterface);
    }

    @Override
    public MenuResponse saveMenu(MenuRequest request){

        if(menuRepository.findByUserInterface(request.getUserInterface()) == null){

            Menu menu = new Menu();
            menu.setUserInterface(request.getUserInterface());
            Menu newMenu = menuRepository.save(menu);

            return MenuResponse.builder()
                    .id(newMenu.getId())
                    .userInterface(newMenu.getUserInterface())
                    .build();
        }

        return null;
    }
}