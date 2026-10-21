package com.example.demo.controller;

import com.example.demo.dto.GlobalResponse;
import com.example.demo.dto.request.BookRequestDTO;
import com.example.demo.dto.response.BookResponseDTO;
import com.example.demo.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<GlobalResponse<List<BookResponseDTO>>> getAllBook(Pageable pageable) {
        return ResponseEntity.ok(bookService.getAllBook(pageable));
    }

    @PostMapping
    public ResponseEntity<GlobalResponse<BookResponseDTO>> createBook(@Valid @RequestBody BookRequestDTO requestDTO) {
        return ResponseEntity.ok(bookService.createBook(requestDTO));
    }
}
