package com.example.tw_thainguyen.service.impl;

import com.example.tw_thainguyen.model.dto.HistoryRequestDTO;
import com.example.tw_thainguyen.model.dto.HistoryResponseDTO;
import com.example.tw_thainguyen.model.dto.PageResponse;
import com.example.tw_thainguyen.model.entity.History;
import com.example.tw_thainguyen.repository.HistoryRepository;
import com.example.tw_thainguyen.service.HistoryService;
import com.example.tw_thainguyen.service.ImageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HistoryServiceImpl extends BaseServiceImpl<History, Long, HistoryRequestDTO, HistoryRequestDTO, HistoryResponseDTO>
        implements HistoryService {
    private final HistoryRepository historyRepository;
    private final ImageService imageService;

    public HistoryServiceImpl(HistoryRepository historyRepository, ImageService imageService) {
        super(historyRepository);
        this.historyRepository = historyRepository;
        this.imageService = imageService;
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
                .image(entity.getImage())
                .build();
    }

    @Override
    protected History toEntity(HistoryRequestDTO historyRequestDTO) {
        String icon = resolveImageUrl(historyRequestDTO.getIconImageId(), historyRequestDTO.getIcon());
        String image = resolveImageUrl(historyRequestDTO.getImageId(), historyRequestDTO.getImage());
        return History.builder()
                .period(historyRequestDTO.getPeriod())
                .title(historyRequestDTO.getTitle())
                .year(historyRequestDTO.getYear())
                .description(historyRequestDTO.getDescription())
                .icon(icon)
                .image(image)
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
        String icon = resolveImageUrl(historyRequestDTO.getIconImageId(), historyRequestDTO.getIcon());
        if (icon != null) {
            entity.setIcon(icon);
        }
        String image = resolveImageUrl(historyRequestDTO.getImageId(), historyRequestDTO.getImage());
        if (image != null) {
            entity.setImage(image);
        }
    }

    /**
     * Resolve image URL from imageId or use provided imageUrl
     */
    private String resolveImageUrl(Long imageId, String imageUrl) {
        if (imageId != null && (imageUrl == null || imageUrl.trim().isEmpty())) {
            return imageService.getImageById(imageId)
                    .map(img -> img.getUrl())
                    .orElse(null);
        }
        return imageUrl;
    }

    @Override
    public PageResponse<HistoryResponseDTO> searchHistory(String keyword, Pageable pageable) {
        Page<History> historyPage = historyRepository.searchHistory(keyword, pageable);
        Page<HistoryResponseDTO> responsePage = historyPage.map(this::toResponseDTO);
        return PageResponse.from(responsePage);
    }
    @Transactional(readOnly = true)
    protected PageResponse<HistoryResponseDTO> findAllPaginated(Pageable pageable) {
        Page<HistoryResponseDTO> page = super.findAll(pageable);
        return PageResponse.from(page);
    }

    private String normalizeSortDir(String sortDir) {
        if (sortDir == null || sortDir.trim().isEmpty()) {
            return "ASC";
        }
        String upper = sortDir.toUpperCase();
        return (upper.equals("ASC") || upper.equals("DESC")) ? upper : "ASC";
    }


    @Override
    public PageResponse<HistoryResponseDTO> getAllHistory(
            Integer page,
            Integer size,
            String sortBy,
            String sortDir,
            String search
    ) {
        if (page == null && size == null) {
            return null;
        }

        int pageNumber = Math.max(0, page != null ? page : 0);
        int pageSize = size != null ? Math.min(Math.max(1, size), 100) : 10;
        String normalizedSortBy = (sortBy == null || sortBy.trim().isEmpty())
                ? "historyId"
                : sortBy;
        String normalizedSortDir = normalizeSortDir(sortDir);

        Pageable pageable = PageRequest.of(
                pageNumber,
                pageSize,
                Sort.by(Sort.Direction.fromString(normalizedSortDir), normalizedSortBy)
        );

        return (search != null && !search.trim().isEmpty())
                ? searchHistory(search.trim(), pageable)
                : findAllPaginated(pageable);
    }

}
