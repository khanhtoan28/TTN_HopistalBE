package com.example.tw_thainguyen.service;

import com.example.tw_thainguyen.model.dto.ArtifactsRequestDTO;
import com.example.tw_thainguyen.model.dto.ArtifactsResponseDTO;
import com.example.tw_thainguyen.model.dto.PageResponse;
import com.example.tw_thainguyen.model.entity.Artifacts;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArtifactsService extends BaseService<Artifacts, Long, ArtifactsRequestDTO, ArtifactsRequestDTO, ArtifactsResponseDTO> {
    
    /**
     * Lấy danh sách các giá trị period duy nhất
     */
    List<String> getAllPeriods();

    /**
     * Lấy danh sách các giá trị type duy nhất
     */
    List<String> getAllTypes();

    /**
     * Lấy danh sách các giá trị space duy nhất
     */
    List<String> getAllSpaces();

    /**
     * Lọc artifacts theo nhiều tiêu chí
     * @param period Period để lọc (có thể null)
     * @param type Type để lọc (có thể null)
     * @param space Space để lọc (có thể null)
     * @return Danh sách artifacts đã được lọc
     */
    List<ArtifactsResponseDTO> filterArtifacts(String period, String type, String space);

    PageResponse<ArtifactsResponseDTO> searchArtifacts(String keyword, Pageable pageable);

    PageResponse<ArtifactsResponseDTO> getAllArtifacts(Integer page, Integer size, String sortBy, String sortDir, String search);
}
