package com.example.tw_thainguyen.service;

import java.util.List;
import java.util.Optional;

/**
 * Base Service interface với các method CRUD cơ bản
 * @param <T> Entity type
 * @param <ID> ID type
 * @param <CreateDTO> DTO cho create operation
 * @param <UpdateDTO> DTO cho update operation
 * @param <ResponseDTO> DTO cho response
 */
public interface BaseService<T, ID, CreateDTO, UpdateDTO, ResponseDTO> {
    
    /**
     * Lấy tất cả entities chưa bị xóa
     */
    List<ResponseDTO> findAll();
    
    /**
     * Lấy entity theo ID
     */
    Optional<ResponseDTO> findById(ID id);
    
    /**
     * Tạo mới entity
     */
    ResponseDTO create(CreateDTO createDTO);
    
    /**
     * Cập nhật entity
     */
    ResponseDTO update(ID id, UpdateDTO updateDTO);
    
    /**
     * Xóa vĩnh viễn entity
     */
    void delete(ID id);
    
    /**
     * Kiểm tra entity có tồn tại không
     */
    boolean existsById(ID id);
}

