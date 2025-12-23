package com.example.tw_thainguyen.service.impl;

import com.example.tw_thainguyen.model.dto.GoldenBookRequestDTO;
import com.example.tw_thainguyen.model.dto.GoldenBookResponseDTO;
import com.example.tw_thainguyen.model.entity.GoldenBook;
import com.example.tw_thainguyen.repository.GoldenBookRepository;
import com.example.tw_thainguyen.service.BaseService;
import com.example.tw_thainguyen.service.GoldenBookService;
import org.springframework.stereotype.Service;

@Service
public class GoldenBookServiceImpl extends BaseServiceImpl<GoldenBook, Long, GoldenBookRequestDTO, GoldenBookRequestDTO, GoldenBookResponseDTO>
        implements GoldenBookService {

    private final GoldenBookRepository goldenBookRepository;

    public GoldenBookServiceImpl(GoldenBookRepository goldenBookRepository) {
        super(goldenBookRepository);
        this.goldenBookRepository = goldenBookRepository;
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
        return GoldenBook.builder()
                .goldenBookName(goldenBookRequestDTO.getGoldenBookName())
                .level(goldenBookRequestDTO.getLevel())
                .image(goldenBookRequestDTO.getImage())
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
        if (goldenBookRequestDTO.getImage() != null) {
            entity.setImage(goldenBookRequestDTO.getImage());
        }
        if (goldenBookRequestDTO.getDescription() != null) {
            entity.setDescription(goldenBookRequestDTO.getDescription());
        }
    }
}
