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
import org.springframework.web.bind.annotation.RestController;

import com.example.tw_thainguyen.model.dto.BaseResponse;
import com.example.tw_thainguyen.model.dto.GoldenBookRequestDTO;
import com.example.tw_thainguyen.model.dto.GoldenBookResponseDTO;
import com.example.tw_thainguyen.model.entity.GoldenBook;
import com.example.tw_thainguyen.service.GoldenBookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/golden-book")
public class GoldenBookController extends BaseController<GoldenBook, Long, GoldenBookRequestDTO, GoldenBookRequestDTO, GoldenBookResponseDTO> {

    public GoldenBookController(GoldenBookService goldenBookService) {
        super(goldenBookService);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<GoldenBookResponseDTO>>> getAllGoldenBooks() {
        return super.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<GoldenBookResponseDTO>> getGoldenBookById(@PathVariable Long id) {
        return super.getById(id);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<GoldenBookResponseDTO>> createGoldenBook(@Valid @RequestBody GoldenBookRequestDTO requestDTO) {
        return super.create(requestDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<GoldenBookResponseDTO>> updateGoldenBook(@PathVariable Long id, @Valid @RequestBody GoldenBookRequestDTO requestDTO) {
        return super.update(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteGoldenBook(@PathVariable Long id) {
        return super.delete(id);
    }
}
