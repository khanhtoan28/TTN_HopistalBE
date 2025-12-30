package com.example.tw_thainguyen.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.tw_thainguyen.model.dto.ArtifactsRequestDTO;
import com.example.tw_thainguyen.model.dto.ArtifactsResponseDTO;
import com.example.tw_thainguyen.model.dto.BaseResponse;
import com.example.tw_thainguyen.model.dto.PageResponse;
import com.example.tw_thainguyen.model.entity.Artifacts;
import com.example.tw_thainguyen.service.ArtifactsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/artifacts")
public class ArtifactsController extends BaseController<Artifacts, Long, ArtifactsRequestDTO, ArtifactsRequestDTO, ArtifactsResponseDTO> {

    private final ArtifactsService artifactsService;

    public ArtifactsController(ArtifactsService artifactsService) {
        super(artifactsService);
        this.artifactsService = artifactsService;
    }

    @GetMapping
    public ResponseEntity<?> getAllArtifacts(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false, defaultValue = "artifactId") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") String sortDir,
            @RequestParam(required = false) String search) {
        PageResponse<ArtifactsResponseDTO> pageResponse = artifactsService.getAllArtifacts(page, size, sortBy, sortDir, search);
        if (pageResponse == null) {
            return super.getAll();
        }
        String message = (search != null && !search.trim().isEmpty()) 
                ? "Tìm kiếm hiện vật thành công" 
                : "Lấy danh sách hiện vật thành công";
        return ResponseEntity.ok(BaseResponse.success(pageResponse, message));
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

    @GetMapping("/filter")
    public ResponseEntity<BaseResponse<List<ArtifactsResponseDTO>>> filterArtifacts(
            @RequestParam(required = false) String period,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String space) {
        List<ArtifactsResponseDTO> artifacts = artifactsService.filterArtifacts(period, type, space);
        return ResponseEntity.ok(BaseResponse.<List<ArtifactsResponseDTO>>success(artifacts, "Lọc danh sách hiện vật thành công"));
    }

    @GetMapping("/periods")
    public ResponseEntity<BaseResponse<List<String>>> getAllPeriods() {
        List<String> periods = artifactsService.getAllPeriods();
        return ResponseEntity.ok(BaseResponse.<List<String>>success(periods, "Lấy danh sách thời kỳ thành công"));
    }

    @GetMapping("/types")
    public ResponseEntity<BaseResponse<List<String>>> getAllTypes() {
        List<String> types = artifactsService.getAllTypes();
        return ResponseEntity.ok(BaseResponse.<List<String>>success(types, "Lấy danh sách loại hình thành công"));
    }

    @GetMapping("/spaces")
    public ResponseEntity<BaseResponse<List<String>>> getAllSpaces() {
        List<String> spaces = artifactsService.getAllSpaces();
        return ResponseEntity.ok(BaseResponse.<List<String>>success(spaces, "Lấy danh sách không gian thành công"));
    }

}
