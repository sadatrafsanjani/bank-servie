package com.sadat.service.general;

import com.sadat.dto.MenuRequest;
import com.sadat.dto.MenuResponse;
import com.sadat.model.Menu;
import java.util.List;

public interface MenuService {

    Menu findMenu(String userInterface);
    List<MenuResponse> getMenus();
    MenuResponse saveMenu(MenuRequest request);
}
