package com.example.tw_thainguyen.repository;

import com.example.tw_thainguyen.model.entity.Artifacts;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArtifactsRepository extends BaseRepository<Artifacts, Long> {
    
    @Query("SELECT DISTINCT a.period FROM Artifacts a WHERE a.period IS NOT NULL ORDER BY a.period DESC")
    List<String> findDistinctPeriods();

    @Query("SELECT DISTINCT a.type FROM Artifacts a WHERE a.type IS NOT NULL ORDER BY a.type DESC")
    List<String> findDistinctTypes();

    @Query("SELECT DISTINCT a.space FROM Artifacts a WHERE a.space IS NOT NULL ORDER BY a.space DESC")
    List<String> findDistinctSpaces();

    /**
     * Tìm artifacts với nhiều điều kiện lọc
     */
    @Query("SELECT a FROM Artifacts a WHERE " +
           "(:period IS NULL OR :period = '' OR a.period = :period) AND " +
           "(:type IS NULL OR :type = '' OR a.type = :type) AND " +
           "(:space IS NULL OR :space = '' OR a.space = :space)")
    List<Artifacts> findByFilters(String period, String type, String space);
}
