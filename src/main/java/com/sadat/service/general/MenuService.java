package com.sadat.service.general;

import com.sadat.dto.general.MenuRequest;
import com.sadat.dto.general.MenuResponse;
import com.sadat.model.Menu;
import java.util.List;

public interface MenuService {

    Menu findMenu(String userInterface);
    List<MenuResponse> getUserMenus();
    MenuResponse saveMenu(MenuRequest request);
}
