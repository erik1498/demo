package com.example.demo.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class BookResponseDTO {
    private UUID uuid;
    private String title;
}
