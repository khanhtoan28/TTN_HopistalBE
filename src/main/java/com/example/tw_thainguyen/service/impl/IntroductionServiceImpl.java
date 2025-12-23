package com.example.tw_thainguyen.service.impl;

import com.example.tw_thainguyen.exception.ResourceNotFoundException;
import com.example.tw_thainguyen.model.dto.IntroductionRequestDTO;
import com.example.tw_thainguyen.model.dto.IntroductionResponseDTO;
import com.example.tw_thainguyen.model.entity.Introduction;
import com.example.tw_thainguyen.repository.IntroductionRepository;
import com.example.tw_thainguyen.service.IntroductionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class IntroductionServiceImpl extends BaseServiceImpl<Introduction, Long, IntroductionRequestDTO, IntroductionRequestDTO, IntroductionResponseDTO>
        implements IntroductionService {

    private final IntroductionRepository introductionRepository;

    public IntroductionServiceImpl(IntroductionRepository introductionRepository) {
        super(introductionRepository);
        this.introductionRepository = introductionRepository;
    }

    @Override
    protected IntroductionResponseDTO toResponseDTO(Introduction introduction) {
        return IntroductionResponseDTO.builder()
                .introductionId(introduction.getIntroductionId())
                .section(introduction.getSection())
                .content(introduction.getContent())
                .createdAt(introduction.getCreatedAt())
                .updatedAt(introduction.getUpdatedAt())
                .build();
    }

    @Override
    protected Introduction toEntity(IntroductionRequestDTO requestDTO) {
        return Introduction.builder()
                .section(requestDTO.getSection())
                .content(requestDTO.getContent())
                .build();
    }

    @Override
    protected void updateEntity(Introduction introduction, IntroductionRequestDTO requestDTO) {
        if (requestDTO.getSection() != null) {
            introduction.setSection(requestDTO.getSection());
        }
        if (requestDTO.getContent() != null) {
            introduction.setContent(requestDTO.getContent());
        }
    }

    @Override
    @Transactional
    public IntroductionResponseDTO update(Long id, IntroductionRequestDTO requestDTO) {
        Introduction introduction = introductionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Giới thiệu", "id", id));

        updateEntity(introduction, requestDTO);
        introduction.setUpdatedAt(LocalDateTime.now());
        Introduction updatedIntroduction = introductionRepository.save(introduction);
        return toResponseDTO(updatedIntroduction);
    }
}
