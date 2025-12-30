package com.example.tw_thainguyen.service.impl;

import com.example.tw_thainguyen.model.dto.GoldenBookRequestDTO;
import com.example.tw_thainguyen.model.dto.GoldenBookResponseDTO;
import com.example.tw_thainguyen.model.dto.PageResponse;
import com.example.tw_thainguyen.model.entity.GoldenBook;
import com.example.tw_thainguyen.repository.GoldenBookRepository;
import com.example.tw_thainguyen.service.GoldenBookService;
import com.example.tw_thainguyen.service.ImageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class GoldenBookServiceImpl extends BaseServiceImpl<GoldenBook, Long, GoldenBookRequestDTO, GoldenBookRequestDTO, GoldenBookResponseDTO>
        implements GoldenBookService {

    private final GoldenBookRepository goldenBookRepository;
    private final ImageService imageService;

    public GoldenBookServiceImpl(GoldenBookRepository goldenBookRepository, ImageService imageService) {
        super(goldenBookRepository);
        this.goldenBookRepository = goldenBookRepository;
        this.imageService = imageService;
    }

    @Override
    protected GoldenBookResponseDTO toResponseDTO(GoldenBook entity) {
        return GoldenBookResponseDTO.builder()
                .goldenBookId(entity.getGoldenBookId())
                .goldenBookName(entity.getGoldenBookName())
                .level(entity.getLevel())
                .year(entity.getYear())
                .department(entity.getDepartment())
                .description(entity.getDescription())
                .image(entity.getImage())
                .build();
    }

    @Override
    protected GoldenBook toEntity(GoldenBookRequestDTO goldenBookRequestDTO) {
        String image = resolveImageUrl(goldenBookRequestDTO.getImageId(), goldenBookRequestDTO.getImage());
        return GoldenBook.builder()
                .goldenBookName(goldenBookRequestDTO.getGoldenBookName())
                .level(goldenBookRequestDTO.getLevel())
                .image(image)
                .year(goldenBookRequestDTO.getYear())
                .department(goldenBookRequestDTO.getDepartment())
                .description(goldenBookRequestDTO.getDescription())
                .build();
    }

    @Override
    protected void updateEntity(GoldenBook entity, GoldenBookRequestDTO goldenBookRequestDTO) {
        if (goldenBookRequestDTO.getGoldenBookName() != null) {
            entity.setGoldenBookName(goldenBookRequestDTO.getGoldenBookName());
        }
        if (goldenBookRequestDTO.getLevel() != null) {
            entity.setLevel(goldenBookRequestDTO.getLevel());
        }
        if (goldenBookRequestDTO.getYear() != null) {
            entity.setYear(goldenBookRequestDTO.getYear());
        }
        if (goldenBookRequestDTO.getDepartment() != null) {
            entity.setDepartment(goldenBookRequestDTO.getDepartment());
        }
        String image = resolveImageUrl(goldenBookRequestDTO.getImageId(), goldenBookRequestDTO.getImage());
        if (image != null) {
            entity.setImage(image);
        }
        if (goldenBookRequestDTO.getDescription() != null) {
            entity.setDescription(goldenBookRequestDTO.getDescription());
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
    public PageResponse<GoldenBookResponseDTO> findByGoldenBook(String keyword, Pageable pageable) {
        Page<GoldenBook> goldenBookPage = goldenBookRepository.searchGoldenBook(keyword, pageable);
        Page<GoldenBookResponseDTO> responseDTOPage = goldenBookPage.map(this::toResponseDTO);
        return PageResponse.from(responseDTOPage);

    }
    
    @Override
    public PageResponse<GoldenBookResponseDTO> getAllGoldenBook(Integer page, Integer size, String sortBy, String sortDir, String search) {
        if (page == null && size == null) {
            return null;
        }

        int pageNumber = Math.max(0, page != null ? page : 0);
        int pageSize = size != null ? Math.min(Math.max(1, size), 100) : 10;
        String normalizedSortBy = (sortBy == null || sortBy.trim().isEmpty()) ? "goldenBookId" : sortBy;
        String normalizedSortDir = normalizeSortDir(sortDir);

        Sort sort = Sort.by(Sort.Direction.fromString(normalizedSortDir), normalizedSortBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        return (search != null && !search.trim().isEmpty())
                ? findByGoldenBook(search.trim(), pageable)
                : findAllPaginated(pageable);
    }

    @Transactional(readOnly = true)
    protected PageResponse<GoldenBookResponseDTO> findAllPaginated(Pageable pageable) {
        Page<GoldenBookResponseDTO> page = super.findAll(pageable);
        return PageResponse.from(page);
    }

    private String normalizeSortDir(String sortDir) {
        if (sortDir == null || sortDir.trim().isEmpty()) {
            return "ASC";
        }
        String upper = sortDir.toUpperCase();
        return (upper.equals("ASC") || upper.equals("DESC")) ? upper : "ASC";
    }
}
