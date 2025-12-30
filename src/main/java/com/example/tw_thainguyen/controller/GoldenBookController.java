package com.example.tw_thainguyen.controller;

import java.util.List;

import com.example.tw_thainguyen.model.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.tw_thainguyen.model.entity.GoldenBook;
import com.example.tw_thainguyen.service.GoldenBookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/golden-book")
public class GoldenBookController extends BaseController<GoldenBook, Long, GoldenBookRequestDTO, GoldenBookRequestDTO, GoldenBookResponseDTO> {

    private final GoldenBookService goldenBookService;
    public GoldenBookController(GoldenBookService goldenBookService) {
        super(goldenBookService);
        this.goldenBookService = goldenBookService;
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

    @GetMapping
    public ResponseEntity<?> getPageAllGoldenBook(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false, defaultValue = "goldenBookId") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") String sortDir,
            @RequestParam(required = false) String search) {
        PageResponse<GoldenBookResponseDTO> pageResponse = goldenBookService.getAllGoldenBook(page, size, sortBy, sortDir, search);
        if (pageResponse == null) {
            return super.getAll();
        }
        String message = (search != null && !search.trim().isEmpty())
                ? "Tìm kiếm sổ vàng thành công"
                : "Lấy danh sách sổ vàng thành công";
        return ResponseEntity.ok(BaseResponse.success(pageResponse, message));
    }
}

