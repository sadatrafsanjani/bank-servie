package com.sadat.controller;

import com.sadat.dto.MenuRequest;
import com.sadat.dto.MenuResponse;
import com.sadat.service.general.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/menus")
public class MenuController {

    private MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public ResponseEntity<List<MenuResponse>> getMenus(){

        List<MenuResponse> responses = menuService.getMenus();

        if(!responses.isEmpty()){

            return ResponseEntity.ok(responses);
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity createMenu(@Valid @RequestBody MenuRequest request){

        if(menuService.saveMenu(request) != null){

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }
}
