package com.example.tw_thainguyen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Base Repository interface với các method chung
 *
 * @param <T>  Entity type
 * @param <ID> ID type
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {

}

