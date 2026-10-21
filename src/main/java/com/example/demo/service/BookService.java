package com.example.demo.service;

import com.example.demo.dto.GlobalResponse;
import com.example.demo.dto.request.BookRequestDTO;
import com.example.demo.dto.response.BookResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    GlobalResponse<List<BookResponseDTO>> getAllBook(Pageable pageable);
    GlobalResponse<BookResponseDTO> createBook(BookRequestDTO requestDTO);
}
