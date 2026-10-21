package com.example.demo.controller;

import com.example.demo.dto.GlobalResponse;
import com.example.demo.dto.request.BookRequestDTO;
import com.example.demo.dto.response.BookResponseDTO;
import com.example.demo.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<GlobalResponse<List<BookResponseDTO>>> getAllBook(Pageable pageable) {
        return ResponseEntity.ok(bookService.getAllBook(pageable));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<GlobalResponse<BookResponseDTO>> createBook(@Valid @RequestBody BookRequestDTO requestDTO) {
        return ResponseEntity.ok(bookService.createBook(requestDTO));
    }
}
