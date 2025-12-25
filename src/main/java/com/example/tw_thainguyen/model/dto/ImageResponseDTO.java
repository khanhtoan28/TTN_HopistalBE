package com.example.tw_thainguyen.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ImageResponseDTO {
    private Long imageId;
    private String filename;
    private String originalFilename;
    private String filePath;
    private String url;
    private Long fileSize;
    private String contentType;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
