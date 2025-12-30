package com.example.tw_thainguyen.controller;

import com.example.tw_thainguyen.model.dto.BaseResponse;
import com.example.tw_thainguyen.model.dto.GoldenBookResponseDTO;
import com.example.tw_thainguyen.model.dto.ImageResponseDTO;
import com.example.tw_thainguyen.model.dto.PageResponse;
import com.example.tw_thainguyen.service.ImageService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<BaseResponse<ImageResponseDTO>> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "description", required = false) String description) {
        ImageResponseDTO image = imageService.uploadImage(file, description);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(image, "Upload ảnh thành công"));
    }

    @PostMapping("/upload/multiple")
    public ResponseEntity<BaseResponse<List<ImageResponseDTO>>> uploadMultipleImages(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam(value = "description", required = false) String description) {
        List<ImageResponseDTO> images = imageService.uploadMultipleImages(files, description);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.<List<ImageResponseDTO>>success(images, "Upload " + images.size() + " ảnh thành công"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ImageResponseDTO>> getImageById(@PathVariable Long id) {
        Optional<ImageResponseDTO> image = imageService.getImageById(id);
        return image.map(img -> ResponseEntity.ok(BaseResponse.<ImageResponseDTO>success(img, "Lấy thông tin ảnh thành công")))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(BaseResponse.<ImageResponseDTO>error("Không tìm thấy ảnh với id: " + id)));
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long id) {
        try {
            String filePath = imageService.getImageFilePath(id);
            Path path = Paths.get(filePath);
            Resource resource = new FileSystemResource(path.toFile());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            // Xác định content type
            String contentType = Files.probeContentType(path);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (com.example.tw_thainguyen.exception.ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<ImageResponseDTO>> updateImage(
            @PathVariable Long id,
            @RequestParam(value = "description", required = false) String description) {
        ImageResponseDTO image = imageService.updateImage(id, description);
        return ResponseEntity.ok(BaseResponse.<ImageResponseDTO>success(image, "Cập nhật ảnh thành công"));
    }

    @PutMapping("/{id}/replace")
    public ResponseEntity<BaseResponse<ImageResponseDTO>> replaceImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile newFile) {
        ImageResponseDTO image = imageService.replaceImage(id, newFile);
        return ResponseEntity.ok(BaseResponse.<ImageResponseDTO>success(image, "Thay thế ảnh thành công"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
        return ResponseEntity.ok(BaseResponse.<Void>success(null, "Xóa ảnh thành công"));
    }

    @GetMapping
    public ResponseEntity<?> getPageAllImage(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false, defaultValue = "imageId") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") String sortDir,
            @RequestParam(required = false) String search) {
        PageResponse<ImageResponseDTO> pageResponse = imageService.getAllImagesPaginated(page, size, sortBy, sortDir, search);
        if (pageResponse == null) {
            return ResponseEntity.badRequest()
                    .body(BaseResponse.error("Không tìm thấy dữ liệu ảnh"));
        }
        String message = (search != null && !search.trim().isEmpty())
                ? "Tìm kiếm hình ảnh thành công"
                : "Lấy danh sách hình ảnh thành công";
        return ResponseEntity.ok(BaseResponse.success(pageResponse, message));
    }
}
