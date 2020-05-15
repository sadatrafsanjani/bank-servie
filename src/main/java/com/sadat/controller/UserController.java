package com.sadat.controller;

import com.sadat.dto.AssignMenuRequest;
import com.sadat.dto.EmailRequest;
import com.sadat.dto.PasswordRequest;
import com.sadat.dto.UserResponse;
import com.sadat.service.general.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers(){

        List<UserResponse> response = userService.getUsers();

        if(!response.isEmpty()){
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/employees")
    public ResponseEntity<List<UserResponse>> getEmployees(){

        List<UserResponse> response = userService.getEmployees();

        if(!response.isEmpty()){
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") long id){

        UserResponse response = userService.getUserById(id);

        if(response != null){

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/change/email/{id}")
    public ResponseEntity changeEmail(@PathVariable("id") long id, @Valid @RequestBody EmailRequest request){

        userService.updateEmail(id, request);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/change/password/{id}")
    public ResponseEntity changePassword(@PathVariable("id") long id, @Valid @RequestBody PasswordRequest request){

        userService.updatePassword(id, request);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/assign/menu/{id}")
    public ResponseEntity assignMenu(@PathVariable("id") long id, @Valid @RequestBody AssignMenuRequest request){

        userService.assignMenu(id, request);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id){

        userService.deleteUser(id);

        return ResponseEntity.ok("User removed!");
    }

    @GetMapping("/activate/{id}")
    public ResponseEntity activateUser(@PathVariable("id") long id){

        if(userService.activateUser(id)){
            return ResponseEntity.ok("User Activated!");
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/deactivate/{id}")
    public ResponseEntity deactivateUser(@PathVariable("id") long id){

        if(userService.deactivateUser(id)){
            return ResponseEntity.ok("User Deactivated!");
        }

        return ResponseEntity.notFound().build();
    }
}
