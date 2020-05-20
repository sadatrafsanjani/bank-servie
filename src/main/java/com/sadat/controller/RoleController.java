package com.sadat.controller;

import com.sadat.dto.RoleRequest;
import com.sadat.dto.RoleResponse;
import com.sadat.service.general.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getRoles(){

        List<RoleResponse> responses = roleService.getRoles();

        if(!responses.isEmpty()){

            return ResponseEntity.ok(responses);
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping("/assign/role/{id}")
    public ResponseEntity updateRole(@PathVariable("id") long id, @Valid @RequestBody RoleRequest request){

        roleService.updateMenu(id, request);

        return ResponseEntity.noContent().build();
    }
}
