package com.sadat.service.general;

import org.springframework.security.core.Authentication;

public interface JwtService {

    Long getExpirationTime();
    String generateToken(Authentication authentication);
    String generateTokenWithUsername(String username);
}
