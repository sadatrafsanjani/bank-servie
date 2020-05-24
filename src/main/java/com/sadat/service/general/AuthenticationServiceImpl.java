package com.sadat.service.general;

import com.sadat.dto.*;
import com.sadat.model.*;
import com.sadat.repository.MenuRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    private UserService userService;
    private MenuRepository menuRepository;
    private RoleService roleService;
    private EmailService emailService;
    private RefreshTokenService refreshTokenService;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @Autowired
    public AuthenticationServiceImpl(UserService userService,
                                     MenuRepository menuRepository,
                                     RoleService roleService,
                                     EmailService emailService,
                                     RefreshTokenService refreshTokenService,
                                     PasswordEncoder passwordEncoder,
                                     AuthenticationManager authenticationManager,
                                     JwtService jwtService) {
        this.userService = userService;
        this.menuRepository = menuRepository;
        this.roleService = roleService;
        this.emailService = emailService;
        this.refreshTokenService = refreshTokenService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    private String encodePassword(String password) {

        return passwordEncoder.encode(password);
    }

    @Override
    public User register(RegisterRequest request) {

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encodePassword(request.getPassword()));

        Set<Menu> menus = new HashSet<>(menuRepository.findUserMenus());

        Set<Role> roles = new HashSet<>();
        Role role = roleService.findRole("ROLE_USER");
        role.setMenus(menus);
        roles.add(role);

        user.setRoles(roles);
        user.setStatus(true);

        emailService.sendPassword(request.getEmail(),request.getUsername(),request.getPassword());

        return userService.saveUser(user);
    }

    @Override
    public LoginResponse login(LoginRequest request){

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );

        try{
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String authenticationToken = jwtService.generateToken(authentication);
            User user = userService.findByUsername(request.getUsername());

            return LoginResponse.builder()
                    .id(user.getId())
                    .username(request.getUsername())
                    .roles(userService.getRoles(user))
                    .loginToken(authenticationToken)
                    .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                    .expiresAt(Instant.now().plusMillis(jwtService.getExpirationTime()))
                    .build();

        }
        catch (Exception e){
            logger.info(e.getMessage());
        }

        return null;
    }

    @Override
    public LoginResponse refreshToken(RefreshTokenRequest request){

        refreshTokenService.validateRefreshToken(request.getRefreshToken());
        String token = jwtService.generateTokenWithUsername(request.getUsername());
        User user = userService.findByUsername(request.getUsername());

        return LoginResponse.builder()
                .id(user.getId())
                .username(request.getUsername())
                .roles(userService.getRoles(user))
                .loginToken(token)
                .refreshToken(request.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtService.getExpirationTime()))
                .build();
    }

    @Override
    public void forgetPassword(EmailRequest request) {

        emailService.forgetPassword(request.getEmail());
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {

        userService.resetPassword(request);
    }

    @Override
    public boolean checkEmailIfExists(EmailRequest request){

        User user = userService.findByEmail(request.getEmail());

        return (user != null);
    }

    @Override
    public boolean checkUsernameIfExists(UsernameRequest request){

        User user = userService.findByUsername(request.getUsername());

        return (user != null);
    }
}
