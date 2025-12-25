package com.example.tw_thainguyen.service.impl;

import com.example.tw_thainguyen.repository.BaseRepository;
import com.example.tw_thainguyen.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Base Service Implementation với các method CRUD cơ bản
 * @param <T> Entity type
 * @param <ID> ID type
 * @param <CreateDTO> DTO cho create operation
 * @param <UpdateDTO> DTO cho update operation
 * @param <ResponseDTO> DTO cho response
 */
public abstract class BaseServiceImpl<T, ID, CreateDTO, UpdateDTO, ResponseDTO> 
        implements BaseService<T, ID, CreateDTO, UpdateDTO, ResponseDTO> {
    
    protected final BaseRepository<T, ID> repository;
    
    public BaseServiceImpl(BaseRepository<T, ID> repository) {
        this.repository = repository;
    }
    
    /**
     * Convert Entity sang ResponseDTO
     */
    protected abstract ResponseDTO toResponseDTO(T entity);
    
    /**
     * Convert CreateDTO sang Entity
     */
    protected abstract T toEntity(CreateDTO createDTO);
    
    /**
     * Update Entity từ UpdateDTO
     */
    protected abstract void updateEntity(T entity, UpdateDTO updateDTO);
    
    @Override
    @Transactional(readOnly = true)
    public List<ResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<ResponseDTO> findById(ID id) {
        return repository.findById(id)
                .map(this::toResponseDTO);
    }
    
    @Override
    @Transactional
    public ResponseDTO create(CreateDTO createDTO) {
        T entity = toEntity(createDTO);
        T savedEntity = repository.save(entity);
        return toResponseDTO(savedEntity);
    }
    
    @Override
    @Transactional
    public ResponseDTO update(ID id, UpdateDTO updateDTO) {
        T entity = repository.findById(id)
                .orElseThrow(() -> new com.example.tw_thainguyen.exception.ResourceNotFoundException("Tài nguyên", "id", id));
        updateEntity(entity, updateDTO);
        T updatedEntity = repository.save(entity);
        return toResponseDTO(updatedEntity);
    }
    
    @Override
    @Transactional
    public void delete(ID id) {
        if (!repository.existsById(id)) {
            throw new com.example.tw_thainguyen.exception.ResourceNotFoundException("Tài nguyên", "id", id);
        }
        repository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }
    
    /**
     * Lấy entity theo ID (internal use)
     */
    protected Optional<T> findEntityById(ID id) {
        return repository.findById(id);
    }
    
    /**
     * Lấy tất cả entities với pagination
     */
    @Transactional(readOnly = true)
    public Page<ResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(this::toResponseDTO);
    }
}

