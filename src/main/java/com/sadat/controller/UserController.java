package com.sadat.controller;

import com.sadat.dto.*;
import com.sadat.service.general.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.IOException;
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

    @GetMapping("/picture/{id}")
    public ResponseEntity<PictureResponse> getProfilePicture(@PathVariable("id") long id){

        PictureResponse response = userService.getProfilePicture(id);

        if(response != null){

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/change/email/{id}")
    public ResponseEntity<?> changeEmail(@PathVariable("id") long id, @Valid @RequestBody EmailRequest request){

        userService.updateEmail(id, request);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/change/pass/{id}")
    public ResponseEntity<?> changePassword(@PathVariable("id") long id, @Valid @RequestBody PasswordRequest request){

        userService.updatePassword(id, request);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id){

        userService.deleteUser(id);

        return ResponseEntity.ok("User removed!");
    }

    @GetMapping("/activate/{id}")
    public ResponseEntity<?> activateUser(@PathVariable("id") long id){

        if(userService.activateUser(id)){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateUser(@PathVariable("id") long id){

        if(userService.deactivateUser(id)){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/picture/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id,
                                    @RequestParam("picture") MultipartFile pictureFile){

        if(userService.getUserById(id) != null){

            try{
                PictureRequest request = PictureRequest.builder()
                        .picture(pictureFile.getBytes())
                        .build();
                userService.updatePicture(id, request);

                return ResponseEntity.noContent().build();
            }
            catch (IOException e){
                logger.error(e.getMessage());
            }
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/assign/menu/{id}")
    public ResponseEntity updateMenu(@PathVariable("id") long id, @Valid @RequestBody UserMenuRequest request){

        userService.updateMenu(id, request);

        return ResponseEntity.noContent().build();
    }
}
