package com.example.tw_thainguyen.repository;

import com.example.tw_thainguyen.model.entity.Artifacts;
import com.example.tw_thainguyen.model.entity.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HistoryRepository extends BaseRepository<History, Long> {
    /**
     * Tìm kiếm lịch sử theo tiêu đề (title).
     * Tìm kiếm không phân biệt hoa thường.
     */
    @Query("SELECT a FROM History a WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR " +
            "LOWER(a.title) LIKE LOWER(CONCAT('%', :keyword, '%'))) OR " +
            "LOWER(a.year) LIKE LOWER(CONCAT('%', :keyword, '%'))"
    )
    Page<History> searchHistory(@Param("keyword") String keyword, Pageable pageable);
}
