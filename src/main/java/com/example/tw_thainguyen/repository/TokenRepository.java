package com.example.tw_thainguyen.repository;

import com.example.tw_thainguyen.model.entity.TokenBlackList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface TokenRepository extends JpaRepository<TokenBlackList, Long> {
    boolean existsByTokenAndExpiryDateAfter(String token, Date date);
}