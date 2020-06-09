package com.sadat.service.general;

import com.sadat.dto.*;
import com.sadat.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {

    List<UserResponse> getUsers();
    List<UserResponse> getEmployees();
    User saveUser(User user);
    void updateEmail(long id, EmailRequest request);
    void updatePassword(long id, PasswordRequest request);
    void resetPassword(ResetPasswordRequest request);
    void deleteUser(long id);
    User findByUsername(String username);
    User findByEmail(String email);
    Set<Long> getRoles(User user);
    UserResponse getUserById(long id);
    void activateUser(long id);
    void deactivateUser(long id);
    void updatePicture(long id, PictureRequest request);
    PictureResponse getProfilePicture(long id);
    void updateMenu(long id, UserMenuRequest request);
}
