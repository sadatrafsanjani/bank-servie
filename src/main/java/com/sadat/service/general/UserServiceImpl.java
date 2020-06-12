package com.sadat.service.general;

import com.sadat.dto.general.*;
import com.sadat.model.Menu;
import com.sadat.model.Role;
import com.sadat.model.User;
import com.sadat.repository.MenuRepository;
import com.sadat.repository.RoleRepository;
import com.sadat.repository.UserRepository;
import com.sadat.utility.Image;
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
    private RoleRepository roleRepository;
    private MenuRepository menuRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           MenuRepository menuRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.menuRepository = menuRepository;
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

            return modelToResponse(user);
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

            response.add(modelToResponse(user));
        }

        return response;
    }

    @Override
    public List<UserResponse> getEmployees() {

        List<UserResponse> response = new ArrayList<>();
        Role role = roleRepository.findByRole("ROLE_ADMIN");

        for(User user : userRepository.findAll()){

            if(!user.getRoles().contains(role)){

                response.add(modelToResponse(user));
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
    public void activateUser(long id){

        userRepository.activateUser(id);
    }

    @Override
    public void deactivateUser(long id){

        userRepository.deactivateUser(id);
    }

    @Override
    public void updatePicture(long id, PictureRequest request){

        userRepository.updatePicture(id, Image.compressBytes(request.getPicture()));
    }

    @Override
    public PictureResponse getProfilePicture(long id){

        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isPresent()){

            User user = userOptional.get();

            byte[] picture = user.getPicture();

            return PictureResponse.builder()
                    .picture(picture != null ? Image.decompressBytes(picture) : null)
                    .build();

        }

        return null;
    }

    private UserResponse modelToResponse(User user){

        byte[] picture = user.getPicture();

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .status(user.isStatus())
                .roles(getRoles(user))
                .picture(picture != null ? Image.decompressBytes(picture) : null)
                .pages(getPages(user.getMenus()))
                .build();
    }

    @Override
    public void updateMenu(long id, UserMenuRequest request) {

        User user = userRepository.findById(id).get();

        Iterator<Menu> iterator = user.getMenus().iterator();

        while(iterator.hasNext()){
            iterator.next();
            iterator.remove();
        }

        user.setMenus(getMenus(request.getMenus()));

        userRepository.save(user);
    }

    private Set<Menu> getMenus(Set<Long> menuSet){

        TreeSet set = new TreeSet(menuSet);
        Set<Menu> menus = new LinkedHashSet<>();

        Iterator<Long> iterator = set.iterator();

        while(iterator.hasNext()){

            Menu menu = menuRepository.getOne(iterator.next());
            menus.add(menu);
        }

        return menus;
    }

    private Set<Long> getPages(Set<Menu> menus){

        Set<Long> menuSet = new LinkedHashSet<>();

        for(Menu menu : menus){
            menuSet.add(menu.getId());
        }

        return menuSet;
    }
}
