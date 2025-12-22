package com.example.tw_thainguyen.controller;

import com.example.tw_thainguyen.model.dto.BaseResponse;
import com.example.tw_thainguyen.service.BaseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Base Controller với các endpoint CRUD cơ bản
 * @param <T> Entity type
 * @param <ID> ID type
 * @param <CreateDTO> DTO cho create operation
 * @param <UpdateDTO> DTO cho update operation
 * @param <ResponseDTO> DTO cho response
 */
public abstract class BaseController<T, ID, CreateDTO, UpdateDTO, ResponseDTO> {
    
    protected final BaseService<T, ID, CreateDTO, UpdateDTO, ResponseDTO> service;
    
    protected BaseController(BaseService<T, ID, CreateDTO, UpdateDTO, ResponseDTO> service) {
        this.service = service;
    }
    
    /**
     * GET / - Lấy tất cả entities
     */
    protected ResponseEntity<BaseResponse<List<ResponseDTO>>> getAll() {
        List<ResponseDTO> data = service.findAll();
        return ResponseEntity.ok(BaseResponse.success(data));
    }
    
    /**
     * GET /{id} - Lấy entity theo ID
     */
    protected ResponseEntity<BaseResponse<ResponseDTO>> getById(ID id) {
        return service.findById(id)
                .map(data -> ResponseEntity.ok(BaseResponse.success(data)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(BaseResponse.error("Entity not found with id: " + id)));
    }
    
    /**
     * POST / - Tạo mới entity
     */
    protected ResponseEntity<BaseResponse<ResponseDTO>> create(@Valid CreateDTO createDTO) {
        ResponseDTO data = service.create(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(data, "Created successfully"));
    }
    
    /**
     * PUT /{id} - Cập nhật entity
     */
    protected ResponseEntity<BaseResponse<ResponseDTO>> update(ID id, @Valid UpdateDTO updateDTO) {
        ResponseDTO data = service.update(id, updateDTO);
        return ResponseEntity.ok(BaseResponse.success(data, "Updated successfully"));
    }
    
    /**
     * DELETE /{id} - Xóa vĩnh viễn entity
     */
    protected ResponseEntity<BaseResponse<Void>> delete(ID id) {
        service.delete(id);
        return ResponseEntity.ok(BaseResponse.success(null, "Deleted successfully"));
    }
}

