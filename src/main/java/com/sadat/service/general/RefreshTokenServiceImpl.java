package com.sadat.service.general;

import com.sadat.exception.CustomException;
import com.sadat.model.RefreshToken;
import com.sadat.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public RefreshToken generateRefreshToken(){

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedOn(Instant.now());

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public void validateRefreshToken(String token) {

        refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new CustomException("Invalid refresh Token"));
    }

    @Override
    public void deleteRefreshToken(String token) {

        refreshTokenRepository.deleteByToken(token);
    }

}
