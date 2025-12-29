package com.example.tw_thainguyen.service;

import com.example.tw_thainguyen.model.dto.HistoryRequestDTO;
import com.example.tw_thainguyen.model.dto.HistoryResponseDTO;
import com.example.tw_thainguyen.model.dto.PageResponse;
import com.example.tw_thainguyen.model.entity.History;
import org.springframework.data.domain.Pageable;

public interface HistoryService extends BaseService<History, Long, HistoryRequestDTO, HistoryRequestDTO, HistoryResponseDTO> {

    PageResponse<HistoryResponseDTO> searchHistory(String keyword, Pageable pageable);

    PageResponse<HistoryResponseDTO> getAllHistory(
            Integer page,
            Integer size,
            String sortBy,
            String sortDir,
            String search
    );


}
