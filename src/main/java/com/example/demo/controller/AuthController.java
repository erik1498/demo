package com.example.demo.controller;

import com.example.demo.dto.request.AuthRequestDTO;
import com.example.demo.dto.request.RefreshTokenRequestDTO;
import com.example.demo.dto.response.AuthResponseDTO;
import com.example.demo.entity.RefreshTokenEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.jwt.JwtService;
import com.example.demo.service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO requestDTO) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDTO.getUsername(), requestDTO.getPassword())
        );

        UserEntity user = (UserEntity) authenticate.getPrincipal();
        String accessToken = jwtService.generateToken(user);
        RefreshTokenEntity refreshTokenEntity = refreshTokenService.createRefreshToken(user.getUuid());

        AuthResponseDTO responseDTO = AuthResponseDTO.builder()
                .token(accessToken)
                .refreshToken(refreshTokenEntity.getToken())
                .build();

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<AuthResponseDTO> refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
        String token = refreshTokenRequestDTO.getRefreshToken();

        return refreshTokenService.findByToken(token)
                .map(refreshTokenService::verifyExpire)
                .map(RefreshTokenEntity::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateToken(user);

                    AuthResponseDTO responseDTO = AuthResponseDTO.builder()
                            .token(accessToken)
                            .refreshToken(token)
                            .build();

                    return ResponseEntity.ok(responseDTO);
                }).orElseThrow(() -> new RuntimeException("Refresh token not found"));
    }
}
