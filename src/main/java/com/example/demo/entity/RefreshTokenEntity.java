package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "refresh_token_tab")
@NoArgsConstructor
@Data
public class RefreshTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(nullable = false, unique = true, name = "token")
    private String token;

    @Column(nullable = false, name = "expiry_date")
    private Instant expiryDate;

    @OneToOne
    @JoinColumn(name = "user_uuid", referencedColumnName = "uuid")
    private UserEntity user;
}
