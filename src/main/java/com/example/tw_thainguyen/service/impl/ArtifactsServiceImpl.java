package com.example.tw_thainguyen.service.impl;

import com.example.tw_thainguyen.exception.ResourceNotFoundException;
import com.example.tw_thainguyen.model.dto.ArtifactsRequestDTO;
import com.example.tw_thainguyen.model.dto.ArtifactsResponseDTO;
import com.example.tw_thainguyen.model.entity.Artifacts;
import com.example.tw_thainguyen.repository.ArtifactsRepository;
import com.example.tw_thainguyen.service.ArtifactsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArtifactsServiceImpl extends BaseServiceImpl<Artifacts, Long, ArtifactsRequestDTO, ArtifactsRequestDTO, ArtifactsResponseDTO> implements ArtifactsService {

    public ArtifactsServiceImpl(ArtifactsRepository artifactsRepository) {
        super(artifactsRepository);
    }

    @Override
    protected ArtifactsResponseDTO toResponseDTO(Artifacts entity) {
        return ArtifactsResponseDTO.builder()
                .artifactId(entity.getArtifactId())
                .artifactName(entity.getArtifactName())
                .description(entity.getDescription())
                .imageUrl(entity.getImageUrl())
                .build();
    }

    @Override
    protected Artifacts toEntity(ArtifactsRequestDTO artifactsRequestDTO) {
        return Artifacts.builder()
                .artifactName(artifactsRequestDTO.getName())
                .description(artifactsRequestDTO.getDescription())
                .imageUrl(artifactsRequestDTO.getImageUrl())
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
}
