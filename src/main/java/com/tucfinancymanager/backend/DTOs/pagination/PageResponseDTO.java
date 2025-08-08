package com.tucfinancymanager.backend.DTOs.pagination;

import java.util.List;

import com.tucfinancymanager.backend.DTOs.transaction.TransactionResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponseDTO<T> {

    private int page;

    private int size;

    private long totalElements;

    private int totalPages;
    
    private boolean last;

    private List<T> content;
}
