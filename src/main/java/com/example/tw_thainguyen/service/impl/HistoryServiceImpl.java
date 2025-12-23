package com.example.tw_thainguyen.service.impl;

import com.example.tw_thainguyen.model.dto.HistoryRequestDTO;
import com.example.tw_thainguyen.model.dto.HistoryResponseDTO;
import com.example.tw_thainguyen.model.entity.History;
import com.example.tw_thainguyen.repository.HistoryRepository;
import com.example.tw_thainguyen.service.HistoryService;
import org.springframework.stereotype.Service;

@Service
public class HistoryServiceImpl extends BaseServiceImpl<History, Long, HistoryRequestDTO, HistoryRequestDTO, HistoryResponseDTO>
        implements HistoryService {
    private final HistoryRepository historyRepository;

    public HistoryServiceImpl(HistoryRepository historyRepository) {
        super(historyRepository);
        this.historyRepository = historyRepository;
    }

    @Override
    protected HistoryResponseDTO toResponseDTO(History entity) {
        return HistoryResponseDTO.builder()
                .historyId(entity.getHistoryId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .year(entity.getYear())
                .icon(entity.getIcon())
                .period(entity.getPeriod())
                .image(entity.getImage()).build();
    }

    @Override
    protected History toEntity(HistoryRequestDTO historyRequestDTO) {
        return History.builder()
                .period(historyRequestDTO.getPeriod())
                .title(historyRequestDTO.getTitle())
                .year(historyRequestDTO.getYear())
                .description(historyRequestDTO.getDescription())
                .icon(historyRequestDTO.getIcon())
                .image(historyRequestDTO.getImage())
                .build();
    }

    @Override
    protected void updateEntity(History entity, HistoryRequestDTO historyRequestDTO) {
        if (historyRequestDTO.getTitle() != null) {
            entity.setTitle(historyRequestDTO.getTitle());
        }
        if (historyRequestDTO.getYear() != null) {
            entity.setYear(historyRequestDTO.getYear());
        }
        if (historyRequestDTO.getPeriod() != null) {
            entity.setPeriod(historyRequestDTO.getPeriod());
        }
        if (historyRequestDTO.getDescription() != null) {
            entity.setDescription(historyRequestDTO.getDescription());
        }
        if (historyRequestDTO.getIcon() != null) {
            entity.setIcon(historyRequestDTO.getIcon());
        }
        if (historyRequestDTO.getImage() != null) {
            entity.setImage(historyRequestDTO.getImage());
        }
    }
}
