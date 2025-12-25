package com.example.tw_thainguyen.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", nullable = false)
    private Long imageId;

    // Tên file được lưu trên Server
    @Column(name = "filename", nullable = false, length = 255)
    private String filename;

    // Tên file gốc của người dùng
    @Column(name = "original_filename", nullable = false, length = 255)
    private String originalFilename;

    // Đường dẫn tới file trên Server
    @Column(name = "file_path", nullable = false, length = 500)
    private String filePath;

    // Kích thước của file
    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    // Loại media của file
    @Column(name = "content_type", length = 100)
    private String contentType;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
