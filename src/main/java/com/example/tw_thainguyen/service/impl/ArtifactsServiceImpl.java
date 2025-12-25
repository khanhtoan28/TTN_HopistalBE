package com.example.tw_thainguyen.service.impl;

import com.example.tw_thainguyen.exception.ResourceNotFoundException;
import com.example.tw_thainguyen.model.dto.ArtifactsRequestDTO;
import com.example.tw_thainguyen.model.dto.ArtifactsResponseDTO;
import com.example.tw_thainguyen.model.entity.Artifacts;
import com.example.tw_thainguyen.repository.ArtifactsRepository;
import com.example.tw_thainguyen.service.ArtifactsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArtifactsServiceImpl extends BaseServiceImpl<Artifacts, Long, ArtifactsRequestDTO, ArtifactsRequestDTO, ArtifactsResponseDTO> implements ArtifactsService {

    private final ArtifactsRepository artifactsRepository;

    public ArtifactsServiceImpl(ArtifactsRepository artifactsRepository) {
        super(artifactsRepository);
        this.artifactsRepository = artifactsRepository;
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
        return Artifacts.builder()
                .artifactName(artifactsRequestDTO.getName())
                .description(artifactsRequestDTO.getDescription())
                .imageUrl(artifactsRequestDTO.getImageUrl())
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
        if (artifactsRequestDTO.getImageUrl() != null) {
            entity.setImageUrl(artifactsRequestDTO.getImageUrl());
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

    @Override
    @Transactional
    public ArtifactsResponseDTO update(Long id, ArtifactsRequestDTO requestDTO) {
        Artifacts artifact = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hiện vật", "id", id));

        updateEntity(artifact, requestDTO);
        Artifacts updatedArtifact = repository.save(artifact);
        return toResponseDTO(updatedArtifact);
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
        // Chuẩn hóa các giá trị null/empty
        String periodFilter = (period != null && !period.trim().isEmpty()) ? period : null;
        String typeFilter = (type != null && !type.trim().isEmpty()) ? type : null;
        String spaceFilter = (space != null && !space.trim().isEmpty()) ? space : null;

        List<Artifacts> artifacts = artifactsRepository.findByFilters(periodFilter, typeFilter, spaceFilter);
        return artifacts.stream()
                .map(this::toResponseDTO)
                .toList();
    }
}
