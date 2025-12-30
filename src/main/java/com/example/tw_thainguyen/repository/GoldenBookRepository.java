package com.example.tw_thainguyen.repository;

import com.example.tw_thainguyen.model.entity.GoldenBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GoldenBookRepository extends BaseRepository<GoldenBook, Long> {

    /**
     * Tìm GoldenBook theo tên và cấp độ
     * Tìm kiếm không phân biệt hoa thường
     */
    @Query("SELECT a FROM GoldenBook a WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR " +
           "LOWER(a.goldenBookName) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
           "LOWER(a.level) LIKE LOWER(CONCAT('%', :keyword, '%')) AND " +
            "LOWER(a.department) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<GoldenBook> searchGoldenBook (@Param("keyword") String keyword, Pageable pageable);

}
