package com.example.demo.service;

import com.example.demo.entity.RefreshTokenEntity;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenService {
    Optional<RefreshTokenEntity> findByToken(String token);
    RefreshTokenEntity createRefreshToken(UUID userID);
    RefreshTokenEntity verifyExpire(RefreshTokenEntity refreshToken);
}
