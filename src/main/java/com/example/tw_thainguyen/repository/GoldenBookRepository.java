package com.example.tw_thainguyen.repository;

import com.example.tw_thainguyen.model.entity.GoldenBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface GoldenBookRepository extends BaseRepository<GoldenBook, Long> {

    /**
     * Tìm GoldenBook theo tên và cấp độ
     * Tìm kiếm không phân biệt hoa thường
     */
    @Query("SELECT a FROM GoldenBook a WHERE " +
           "(:name IS NULL OR :name = '' OR LOWER(a.goldenBookName) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:level IS NULL OR :level = '' OR LOWER(a.level) LIKE LOWER(CONCAT('%', :level, '%')))")
    Page<GoldenBook> findByGoldenBookAndLevelContainingIgnoreCase(String name, String level, Pageable pageable);
}
