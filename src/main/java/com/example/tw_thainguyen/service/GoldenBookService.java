package com.example.tw_thainguyen.service;

import com.example.tw_thainguyen.model.dto.GoldenBookRequestDTO;
import com.example.tw_thainguyen.model.dto.GoldenBookResponseDTO;
import com.example.tw_thainguyen.model.dto.PageResponse;
import com.example.tw_thainguyen.model.entity.GoldenBook;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface GoldenBookService extends BaseService<GoldenBook, Long, GoldenBookRequestDTO, GoldenBookRequestDTO, GoldenBookResponseDTO> {

    PageResponse<GoldenBookResponseDTO> findByGoldenBook(String keyword, Pageable pageable);
    PageResponse<GoldenBookResponseDTO> getAllGoldenBook (Integer page, Integer size, String sortBy, String sortDir, String search);
}
