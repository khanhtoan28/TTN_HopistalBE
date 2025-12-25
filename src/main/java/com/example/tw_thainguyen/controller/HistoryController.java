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
import com.example.tw_thainguyen.model.dto.HistoryRequestDTO;
import com.example.tw_thainguyen.model.dto.HistoryResponseDTO;
import com.example.tw_thainguyen.model.entity.History;
import com.example.tw_thainguyen.service.HistoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/histories")
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
