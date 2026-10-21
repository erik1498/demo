package com.example.demo.service.impl;

import com.example.demo.entity.RefreshTokenEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.RefreshTokenRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${application.jwt.refresh-expire}")
    private Long refreshTokenExpiryTime;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Override
    public Optional<RefreshTokenEntity> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    @Transactional
    public RefreshTokenEntity createRefreshToken(UUID userID) {
        UserEntity userEntity = userRepository.findById(userID).orElseThrow(() -> new RuntimeException("User not found"));

        refreshTokenRepository.deleteByUser(userEntity);

        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
        refreshTokenEntity.setUser(userEntity);
        refreshTokenEntity.setExpiryDate(Instant.now().plusMillis(refreshTokenExpiryTime));
        refreshTokenEntity.setToken(UUID.randomUUID().toString());

        return refreshTokenRepository.save(refreshTokenEntity);
    }

    @Override
    public RefreshTokenEntity verifyExpire(RefreshTokenEntity refreshToken) {
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token expired");
        }
        return refreshToken;
    }
}
