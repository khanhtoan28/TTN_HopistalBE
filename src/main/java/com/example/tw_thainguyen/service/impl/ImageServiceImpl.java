package com.example.tw_thainguyen.service.impl;

import com.example.tw_thainguyen.exception.ResourceNotFoundException;
import com.example.tw_thainguyen.model.dto.ImageResponseDTO;
import com.example.tw_thainguyen.model.entity.Image;
import com.example.tw_thainguyen.repository.ImageRepository;
import com.example.tw_thainguyen.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);
    
    private final ImageRepository imageRepository;
    private final String uploadDir;

    public ImageServiceImpl(ImageRepository imageRepository,
                           @Value("${app.upload.dir:uploads}") String uploadDir) {
        this.imageRepository = imageRepository;
        this.uploadDir = uploadDir;
        createUploadDirectoryIfNotExists();
    }

    private void createUploadDirectoryIfNotExists() {
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Không thể tạo thư mục upload: " + uploadDir, e);
        }
    }

    @Override
    @Transactional
    public ImageResponseDTO uploadImage(MultipartFile file, String description) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File không được để trống");
        }

        try {
            String uniqueFilename = generateUniqueFilename(file.getOriginalFilename());
            Path targetPath = Paths.get(uploadDir, uniqueFilename);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            Image image = Image.builder()
                    .filename(uniqueFilename)
                    .originalFilename(file.getOriginalFilename() != null ? file.getOriginalFilename() : "unknown")
                    .filePath(targetPath.toString())
                    .fileSize(file.getSize())
                    .contentType(file.getContentType())
                    .description(description)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            Image savedImage = imageRepository.save(image);
            logger.info("Upload ảnh thành công: {}", uniqueFilename);
            return toResponseDTO(savedImage);
        } catch (IOException e) {
            logger.error("Lỗi khi lưu file: {}", e.getMessage(), e);
            throw new RuntimeException("Lỗi khi lưu file: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public List<ImageResponseDTO> uploadMultipleImages(MultipartFile[] files, String description) {
        List<ImageResponseDTO> uploadedImages = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                uploadedImages.add(uploadImage(file, description));
            }
        }
        return uploadedImages;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImageResponseDTO> getAllImages() {
        return imageRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ImageResponseDTO> getImageById(Long id) {
        return imageRepository.findById(id)
                .map(this::toResponseDTO);
    }

    @Override
    @Transactional
    public void deleteImage(Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ảnh", "id", id));

        // Xóa file từ filesystem
        try {
            Path filePath = Paths.get(image.getFilePath());
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
        } catch (IOException e) {
            // Log error nhưng vẫn xóa record trong DB
            logger.error("Lỗi khi xóa file với id {}: {}", id, e.getMessage());
        }

        // Xóa record trong database
        imageRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public String getImageFilePath(Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ảnh", "id", id));
        return image.getFilePath();
    }

    @Override
    @Transactional
    public ImageResponseDTO updateImage(Long id, String description) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ảnh", "id", id));

        if (description != null) {
            image.setDescription(description);
        }
        image.setUpdatedAt(LocalDateTime.now());

        Image updatedImage = imageRepository.save(image);
        return toResponseDTO(updatedImage);
    }

    @Override
    @Transactional
    public ImageResponseDTO replaceImage(Long id, MultipartFile newFile) {
        if (newFile.isEmpty()) {
            throw new IllegalArgumentException("File không được để trống");
        }

        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ảnh", "id", id));

        try {
            // Xóa file cũ
            Path oldFilePath = Paths.get(image.getFilePath());
            if (Files.exists(oldFilePath)) {
                Files.delete(oldFilePath);
                logger.info("Đã xóa file cũ: {}", image.getFilePath());
            }

            // Tạo tên file mới unique và lưu
            String uniqueFilename = generateUniqueFilename(newFile.getOriginalFilename());
            Path targetPath = Paths.get(uploadDir, uniqueFilename);
            Files.copy(newFile.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            // Cập nhật thông tin trong database
            image.setFilename(uniqueFilename);
            image.setOriginalFilename(newFile.getOriginalFilename() != null ? newFile.getOriginalFilename() : "unknown");
            image.setFilePath(targetPath.toString());
            image.setFileSize(newFile.getSize());
            image.setContentType(newFile.getContentType());
            image.setUpdatedAt(LocalDateTime.now());

            Image updatedImage = imageRepository.save(image);
            logger.info("Thay thế ảnh thành công: {} -> {}", image.getImageId(), uniqueFilename);
            return toResponseDTO(updatedImage);
        } catch (IOException e) {
            logger.error("Lỗi khi thay thế file với id {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Lỗi khi thay thế file: " + e.getMessage(), e);
        }
    }

    /**
     * Tạo tên file unique từ original filename
     */
    private String generateUniqueFilename(String originalFilename) {
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        return UUID.randomUUID().toString() + extension;
    }

    private ImageResponseDTO toResponseDTO(Image image) {
        // Tạo URL để truy cập ảnh
        String url = "/api/v1/images/" + image.getImageId() + "/download";
        
        return ImageResponseDTO.builder()
                .imageId(image.getImageId())
                .filename(image.getFilename())
                .originalFilename(image.getOriginalFilename())
                .filePath(image.getFilePath())
                .url(url)
                .fileSize(image.getFileSize())
                .contentType(image.getContentType())
                .description(image.getDescription())
                .createdAt(image.getCreatedAt())
                .updatedAt(image.getUpdatedAt())
                .build();
    }
}
