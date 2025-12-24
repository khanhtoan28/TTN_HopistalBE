package com.example.tw_thainguyen.controller;

import com.example.tw_thainguyen.model.dto.BaseResponse;
import com.example.tw_thainguyen.model.dto.HistoryRequestDTO;
import com.example.tw_thainguyen.model.dto.HistoryResponseDTO;
import com.example.tw_thainguyen.model.entity.History;
import com.example.tw_thainguyen.service.HistoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/histories")
public class HistoryController extends BaseController<History, Long, HistoryRequestDTO, HistoryRequestDTO, HistoryResponseDTO> {

    public HistoryController(HistoryService historyService) {
        super(historyService);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<HistoryResponseDTO>>> getAllHistories() {
        return super.getAll();
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
}
