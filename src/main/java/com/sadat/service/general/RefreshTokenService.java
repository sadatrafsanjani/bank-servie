package com.sadat.service.general;

import com.sadat.model.RefreshToken;

public interface RefreshTokenService {

    RefreshToken generateRefreshToken();
    void validateRefreshToken(String token);
    void deleteRefreshToken(String token);
}
