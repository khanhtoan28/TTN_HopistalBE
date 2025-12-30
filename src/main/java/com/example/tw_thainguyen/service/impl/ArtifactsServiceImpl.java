package com.example.tw_thainguyen.service.impl;

import com.example.tw_thainguyen.model.dto.ArtifactsRequestDTO;
import com.example.tw_thainguyen.model.dto.ArtifactsResponseDTO;
import com.example.tw_thainguyen.model.dto.PageResponse;
import com.example.tw_thainguyen.model.entity.Artifacts;
import com.example.tw_thainguyen.repository.ArtifactsRepository;
import com.example.tw_thainguyen.service.ArtifactsService;
import com.example.tw_thainguyen.service.ImageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ArtifactsServiceImpl extends BaseServiceImpl<Artifacts, Long, ArtifactsRequestDTO, ArtifactsRequestDTO, ArtifactsResponseDTO> implements ArtifactsService {

    private final ArtifactsRepository artifactsRepository;
    private final ImageService imageService;

    public ArtifactsServiceImpl(ArtifactsRepository artifactsRepository, ImageService imageService) {
        super(artifactsRepository);
        this.artifactsRepository = artifactsRepository;
        this.imageService = imageService;
    }

    @Override
    protected ArtifactsResponseDTO toResponseDTO(Artifacts entity) {
        return ArtifactsResponseDTO.builder()
                .artifactId(entity.getArtifactId())
                .artifactName(entity.getArtifactName())
                .description(entity.getDescription())
                .imageUrl(entity.getImageUrl())
                .period(entity.getPeriod())
                .type(entity.getType())
                .space(entity.getSpace())
                .build();
    }

    @Override
    protected Artifacts toEntity(ArtifactsRequestDTO artifactsRequestDTO) {
        String imageUrl = resolveImageUrl(artifactsRequestDTO.getImageId(), artifactsRequestDTO.getImageUrl());
        return Artifacts.builder()
                .artifactName(artifactsRequestDTO.getName())
                .description(artifactsRequestDTO.getDescription())
                .imageUrl(imageUrl)
                .period(artifactsRequestDTO.getPeriod())
                .type(artifactsRequestDTO.getType())
                .space(artifactsRequestDTO.getSpace())
                .build();
    }

    @Override
    protected void updateEntity(Artifacts entity, ArtifactsRequestDTO artifactsRequestDTO) {
        if (artifactsRequestDTO.getName() != null) {
            entity.setArtifactName(artifactsRequestDTO.getName());
        }
        if (artifactsRequestDTO.getDescription() != null) {
            entity.setDescription(artifactsRequestDTO.getDescription());
        }
        String imageUrl = resolveImageUrl(artifactsRequestDTO.getImageId(), artifactsRequestDTO.getImageUrl());
        if (imageUrl != null) {
            entity.setImageUrl(imageUrl);
        }
        if (artifactsRequestDTO.getPeriod() != null) {
            entity.setPeriod(artifactsRequestDTO.getPeriod());
        }
        if (artifactsRequestDTO.getType() != null) {
            entity.setType(artifactsRequestDTO.getType());
        }
        if (artifactsRequestDTO.getSpace() != null) {
            entity.setSpace(artifactsRequestDTO.getSpace());
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
    @Transactional(readOnly = true)
    public List<String> getAllPeriods() {
        return artifactsRepository.findDistinctPeriods();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllTypes() {
        return artifactsRepository.findDistinctTypes();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllSpaces() {
        return artifactsRepository.findDistinctSpaces();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArtifactsResponseDTO> filterArtifacts(String period, String type, String space) {
        List<Artifacts> artifacts = artifactsRepository.findByFilters(
                normalizeString(period), 
                normalizeString(type), 
                normalizeString(space)
        );
        return artifacts.stream().map(this::toResponseDTO).toList();
    }

    @Override
    public PageResponse<ArtifactsResponseDTO> searchArtifacts(String keyword, Pageable pageable) {
        Page<Artifacts> artifactPage = artifactsRepository.searchArtifacts(normalizeString(keyword), pageable);
        Page<ArtifactsResponseDTO> dtoPage = artifactPage.map(this::toResponseDTO);
        return PageResponse.from(dtoPage);
    }

    @Transactional(readOnly = true)
    protected PageResponse<ArtifactsResponseDTO> findAllPaginated(Pageable pageable) {
        Page<ArtifactsResponseDTO> page = super.findAll(pageable);
        return PageResponse.from(page);
    }

    private String normalizeSortDir(String sortDir) {
        if (sortDir == null || sortDir.trim().isEmpty()) {
            return "ASC";
        }
        String upper = sortDir.toUpperCase();
        return (upper.equals("ASC") || upper.equals("DESC")) ? upper : "ASC";
    }

    private String normalizeString(String value) {
        return (value != null && !value.trim().isEmpty()) ? value : null;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ArtifactsResponseDTO> getAllArtifacts(Integer page, Integer size, String sortBy, String sortDir, String search) {
        if (page == null && size == null) {
            return null;
        }
        
        int pageNumber = Math.max(0, page != null ? page : 0);
        int pageSize = size != null ? Math.min(Math.max(1, size), 100) : 10;
        String normalizedSortBy = (sortBy == null || sortBy.trim().isEmpty()) ? "artifactId" : sortBy;
        String normalizedSortDir = normalizeSortDir(sortDir);
        
        Sort sort = Sort.by(Sort.Direction.fromString(normalizedSortDir), normalizedSortBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        
        return (search != null && !search.trim().isEmpty()) 
                ? searchArtifacts(search.trim(), pageable) 
                : findAllPaginated(pageable);
    }
}
