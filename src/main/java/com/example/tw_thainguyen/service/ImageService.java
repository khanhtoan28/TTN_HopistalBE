package com.example.tw_thainguyen.service;

import com.example.tw_thainguyen.model.dto.ImageResponseDTO;
import com.example.tw_thainguyen.model.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    
    /**
     * Upload một ảnh mới
     */
    ImageResponseDTO uploadImage(MultipartFile file, String description);

    /**
     * Upload nhiều ảnh cùng lúc
     */
    List<ImageResponseDTO> uploadMultipleImages(MultipartFile[] files, String description);

    /**
     * Lấy tất cả ảnh
     */
    List<ImageResponseDTO> getAllImages();

    /**
     * Lấy ảnh theo ID
     */
    Optional<ImageResponseDTO> getImageById(Long id);

    /**
     * Xóa ảnh (xóa cả file và record trong DB)
     */
    void deleteImage(Long id);

    /**
     * Lấy file path của ảnh
     */
    String getImageFilePath(Long id);

    /**
     * Cập nhật thông tin ảnh (description)
     */
    ImageResponseDTO updateImage(Long id, String description);

    /**
     * Thay thế ảnh (upload file mới thay thế file cũ)
     */
    ImageResponseDTO replaceImage(Long id, MultipartFile newFile);
}
