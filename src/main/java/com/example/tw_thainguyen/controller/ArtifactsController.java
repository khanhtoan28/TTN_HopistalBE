package com.example.tw_thainguyen.controller;

import com.example.tw_thainguyen.model.dto.ArtifactsRequestDTO;
import com.example.tw_thainguyen.model.dto.ArtifactsResponseDTO;
import com.example.tw_thainguyen.model.dto.BaseResponse;
import com.example.tw_thainguyen.model.entity.Artifacts;
import com.example.tw_thainguyen.service.ArtifactsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/artifacts")
public class ArtifactsController extends BaseController<Artifacts, Long, ArtifactsRequestDTO, ArtifactsRequestDTO, ArtifactsResponseDTO> {

    public ArtifactsController(ArtifactsService artifactsService) {
        super(artifactsService);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<ArtifactsResponseDTO>>> getAllArtifacts() {
        return super.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ArtifactsResponseDTO>> getArtifactById(@PathVariable Long id) {
        return super.getById(id);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<ArtifactsResponseDTO>> createArtifacts(@Valid @RequestBody ArtifactsRequestDTO requestDTO) {
        return super.create(requestDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<ArtifactsResponseDTO>> updateArtifacts(@PathVariable Long id, @Valid @RequestBody ArtifactsRequestDTO requestDTO) {
        return super.update(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteArtifacts(@PathVariable Long id) {
        return super.delete(id);
    }

}
