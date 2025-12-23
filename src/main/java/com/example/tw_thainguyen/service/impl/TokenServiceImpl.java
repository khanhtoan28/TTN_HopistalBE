package com.example.tw_thainguyen.service.impl;

import com.example.tw_thainguyen.model.entity.TokenBlackList;
import com.example.tw_thainguyen.repository.TokenRepository;
import com.example.tw_thainguyen.service.TokenService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void invalidateToken(String token) {
        tokenRepository.save(TokenBlackList.builder()
                .token(token)
                .expiryDate(new Date(System.currentTimeMillis() + 86400000))
                .build());
    }

    @Override
    public boolean isTokenInvalidated(String token) {
        return tokenRepository.existsByTokenAndExpiryDateAfter(token, new Date());
    }
}
