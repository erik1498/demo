package com.example.demo.service.impl;

import com.example.demo.dto.GlobalResponse;
import com.example.demo.dto.request.BookRequestDTO;
import com.example.demo.dto.response.BookResponseDTO;
import com.example.demo.entity.BookEntity;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private BookResponseDTO mapToResponse(BookEntity entity) {
        return BookResponseDTO.builder()
                .uuid(entity.getUuid())
                .title(entity.getTitle())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public GlobalResponse<List<BookResponseDTO>> getAllBook(Pageable pageable) {
        Page<BookEntity> entityPage = bookRepository.findAll(pageable);

        List<BookResponseDTO> responseDTOS = entityPage.getContent().stream().map(this::mapToResponse).toList();

        GlobalResponse.PaginationMeta meta = GlobalResponse.PaginationMeta.builder()
                .currentPage(entityPage.getNumber())
                .totalElements(entityPage.getTotalElements())
                .pageSize(entityPage.getSize())
                .isLast(entityPage.isLast())
                .build();

        return GlobalResponse.<List<BookResponseDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Success")
                .data(responseDTOS)
                .paginationMeta(meta)
                .build();
    }

    @Override
    @Transactional
    public GlobalResponse<BookResponseDTO> createBook(BookRequestDTO requestDTO) {
        BookEntity entity = new BookEntity();
        entity.setTitle(requestDTO.getTitle());

        bookRepository.saveAndFlush(entity);

        BookResponseDTO bookResponseDTO = mapToResponse(entity);

        return GlobalResponse.<BookResponseDTO>builder()
                .code(HttpStatus.CREATED.value())
                .message("Success")
                .data(bookResponseDTO)
                .build();
    }
}
