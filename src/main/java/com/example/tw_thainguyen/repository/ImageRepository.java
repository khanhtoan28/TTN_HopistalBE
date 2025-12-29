package com.example.tw_thainguyen.repository;

import com.example.tw_thainguyen.model.dto.ImageResponseDTO;
import com.example.tw_thainguyen.model.entity.History;
import com.example.tw_thainguyen.model.entity.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImageRepository extends BaseRepository<Image, Long> {
    /**
     * Tìm kiếm theo tên ảnh
     */
    @Query("SELECT i FROM Image i WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR " +
            "LOWER(i.filename) LIKE LOWER(CONCAT('%', :imageName, '%')))"
    )
    Page<Image> searchImage(@Param("keyword") String keyword, Pageable pageable);
}
