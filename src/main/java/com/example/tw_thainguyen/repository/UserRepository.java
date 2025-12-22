package com.example.tw_thainguyen.repository;

import com.example.tw_thainguyen.model.entity.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long> {
    
    Optional<User> findUserByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}