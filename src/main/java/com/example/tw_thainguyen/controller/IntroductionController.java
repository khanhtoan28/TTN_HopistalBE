package com.example.tw_thainguyen.controller;

import com.example.tw_thainguyen.model.dto.BaseResponse;
import com.example.tw_thainguyen.model.dto.IntroductionRequestDTO;
import com.example.tw_thainguyen.model.dto.IntroductionResponseDTO;
import com.example.tw_thainguyen.model.entity.Introduction;
import com.example.tw_thainguyen.service.IntroductionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/introductions")
public class IntroductionController extends BaseController<Introduction, Long, IntroductionRequestDTO, IntroductionRequestDTO, IntroductionResponseDTO> {

    public IntroductionController(IntroductionService introductionService) {
        super(introductionService);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<IntroductionResponseDTO>>> getAllIntroductions() {
        return super.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<IntroductionResponseDTO>> getIntroductionById(@PathVariable Long id) {
        return super.getById(id);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<IntroductionResponseDTO>> createIntroduction(@Valid @RequestBody IntroductionRequestDTO requestDTO) {
        return super.create(requestDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<IntroductionResponseDTO>> updateIntroduction(@PathVariable Long id, @Valid @RequestBody IntroductionRequestDTO requestDTO) {
        return super.update(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteIntroduction(@PathVariable Long id) {
        return super.delete(id);
    }
}
