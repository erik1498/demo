package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GlobalResponse<T> {
    private Integer code;
    private String message;
    private T data;
    private PaginationMeta paginationMeta;

    @Data
    @Builder
    public static class PaginationMeta {
        private int currentPage;
        private int pageSize;
        private long totalElements;
        private int totalPages;
        private boolean isLast;
    }
}
