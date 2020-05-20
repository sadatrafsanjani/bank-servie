package com.sadat.service.general;

import com.sadat.dto.*;
import com.sadat.model.Menu;
import com.sadat.model.Role;
import com.sadat.model.User;
import com.sadat.repository.MenuRepository;
import com.sadat.repository.RoleRepository;
import com.sadat.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserRepository userRepository;
    private MenuRepository menuRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           MenuRepository menuRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.menuRepository = menuRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private String encodePassword(String password) {

        return passwordEncoder.encode(password);
    }

    @Override
    public UserResponse getUserById(long id){

        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isPresent()){

            User user = userOptional.get();

            UserResponse response = UserResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .roles(getRoles(user))
                    .build();

            return response;
        }

        return null;
    }

    @Override
    public User findByUsername(String username) {

        Optional<User> userOptional = userRepository.findByUsername(username);

        if(userOptional.isPresent()){

            return userOptional.get();
        }

        return null;
    }

    @Override
    public User findByEmail(String email) {

        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isPresent()){

            return userOptional.get();
        }

        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = findByUsername(username);

        if(user != null){
            return new UserDetailsImpl(user);
        }
        else{
            throw new UsernameNotFoundException(username);
        }
    }

    @Override
    public List<UserResponse> getUsers() {

        List<UserResponse> response = new ArrayList<>();

        for(User user : userRepository.findAll()){

            UserResponse userResponse = UserResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .status(user.isStatus())
                    .roles(getRoles(user))
                    .build();

            response.add(userResponse);
        }

        return response;
    }

    @Override
    public List<UserResponse> getEmployees() {

        List<UserResponse> response = new ArrayList<>();
        Role role = roleRepository.findByRole("ROLE_ADMIN");

        for(User user : userRepository.findAll()){

            if(!user.getRoles().contains(role)){

                UserResponse userResponse = UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .status(user.isStatus())
                        .roles(getRoles(user))
                        .build();

                response.add(userResponse);
            }
        }

        return response;
    }

    @Override
    public Set<Long> getRoles(User user){

        Set<Long> roles = new HashSet<>();

        for(Role role : user.getRoles()){
            roles.add(role.getId());
        }

        return roles;
    }

    @Override
    public User saveUser(User user){

        return userRepository.save(user);
    }

    @Override
    public void updateEmail(long id, EmailRequest request){

        userRepository.updateEmail(request.getEmail(), id);
    }

    @Override
    public void updatePassword(long id, PasswordRequest request) {

        userRepository.updatePassword(encodePassword(request.getPassword()), id);
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {

        userRepository.resetPassword(encodePassword(request.getPassword()), request.getEmail());
    }

    @Override
    public void deleteUser(long id){

        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isPresent()){

            userRepository.deleteById(id);
        }
    }

    @Override
    public boolean activateUser(long id){

        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isPresent()){
            userRepository.activateUser(id);
        }

        User user = userRepository.getOne(id);

        return user.isStatus() ? true : false;
    }

    @Override
    public boolean deactivateUser(long id){

        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isPresent()){
            userRepository.deactivateUser(id);
        }

        User user = userRepository.getOne(id);

        return !user.isStatus() ? true : false;
    }

    @Override
    public List<AllowedMenuResponse> getUserAllowedMenus(long id){

        List<AllowedMenuResponse> responses = new ArrayList<>();

        User user = userRepository.getOne(id);

        for(Role role : user.getRoles()){

            AllowedMenuResponse response = AllowedMenuResponse.builder()
                    .role(role.getRole().equals("ROLE_ADMIN") ? "Admin" : "User")
                    .menus(getPages(role.getMenus()))
                    .build();

            responses.add(response);
        }

        return responses;
    }

    @Override
    public void assignMenu(long id, AssignMenuRequest request){

        User user = userRepository.getOne(id);
        Set<Menu> updatedMenus = getMenus(request.getMenus());
        Set<Role> roles = new HashSet<>();

        for(Role role : user.getRoles()){

            role.setMenus(updatedMenus);
            roles.add(role);
        }

        userRepository.updateRoleMenus(id, roles);
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
