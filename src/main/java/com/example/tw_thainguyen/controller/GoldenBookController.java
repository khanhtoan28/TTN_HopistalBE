package com.example.tw_thainguyen.controller;

import com.example.tw_thainguyen.model.dto.BaseResponse;
import com.example.tw_thainguyen.model.dto.GoldenBookRequestDTO;
import com.example.tw_thainguyen.model.dto.GoldenBookResponseDTO;
import com.example.tw_thainguyen.model.entity.GoldenBook;
import com.example.tw_thainguyen.service.GoldenBookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/golden-book")
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
