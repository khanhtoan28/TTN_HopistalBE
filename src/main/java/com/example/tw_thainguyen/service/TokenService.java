package com.example.tw_thainguyen.service;


import org.springframework.stereotype.Service;


public interface TokenService {
    void invalidateToken(String token);

    boolean isTokenInvalidated(String token);
}
