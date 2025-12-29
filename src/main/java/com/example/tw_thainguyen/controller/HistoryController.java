package com.example.tw_thainguyen.controller;

import java.util.List;

import com.example.tw_thainguyen.model.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.tw_thainguyen.model.entity.History;
import com.example.tw_thainguyen.service.HistoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/histories")
public class HistoryController extends BaseController<History, Long, HistoryRequestDTO, HistoryRequestDTO, HistoryResponseDTO> {

    private final HistoryService historyService;
    public HistoryController(HistoryService historyService) {
        super(historyService);
        this.historyService = historyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<HistoryResponseDTO>> getHistoryById(@PathVariable Long id) {
        return super.getById(id);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<HistoryResponseDTO>> createHistory(@Valid @RequestBody HistoryRequestDTO requestDTO) {
        return super.create(requestDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<HistoryResponseDTO>> updateHistory(@PathVariable Long id, @Valid @RequestBody HistoryRequestDTO requestDTO) {
        return super.update(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteHistory(@PathVariable Long id) {
        return super.delete(id);
    }

    @GetMapping
    public ResponseEntity<?> getPageAllHistory(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false, defaultValue = "historyId") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") String sortDir,
            @RequestParam(required = false) String search) {
        PageResponse<HistoryResponseDTO> pageResponse = historyService.getAllHistory(page, size, sortBy, sortDir, search);
        if (pageResponse == null) {
            return super.getAll();
        }
        String message = (search != null && !search.trim().isEmpty())
                ? "Tìm kiếm lịch sử thành công"
                : "Lấy danh sách lịch sử thành công";
        return ResponseEntity.ok(BaseResponse.success(pageResponse, message));
    }
}
