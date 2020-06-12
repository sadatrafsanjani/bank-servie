package com.sadat.controller;

import com.sadat.dto.general.*;
import com.sadat.service.general.AuthenticationService;
import com.sadat.service.general.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    private AuthenticationService authenticationService;
    private RefreshTokenService refreshTokenService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService,
                                    RefreshTokenService refreshTokenService) {
        this.authenticationService = authenticationService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegisterRequest request){

        if(authenticationService.register(request) != null){

            return ResponseEntity.ok("User Created Successfully!");
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request){

        LoginResponse response = authenticationService.login(request);

        if(response != null){

            return ResponseEntity.ok(response);
        }

        return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshTokens(@Valid @RequestBody RefreshTokenRequest request) {

        return ResponseEntity.ok(authenticationService.refreshToken(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest request) {

        refreshTokenService.deleteRefreshToken(request.getRefreshToken());

        return ResponseEntity.ok("Logged out Successfully!!");
    }

    @PostMapping("/check/username")
    public ResponseEntity checkUsername(@Valid @RequestBody UsernameRequest request){

        if(authenticationService.checkUsernameIfExists(request)){

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/check/email")
    public ResponseEntity checkEmail(@Valid @RequestBody EmailRequest request){

        if(authenticationService.checkEmailIfExists(request)){

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/password/forget")
    public ResponseEntity forgetPassword(@Valid @RequestBody EmailRequest request){

        authenticationService.forgetPassword(request);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/password/reset")
    public ResponseEntity resetPassword(@Valid @RequestBody ResetPasswordRequest request){

        authenticationService.resetPassword(request);

        return ResponseEntity.ok().build();
    }
}
