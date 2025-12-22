package com.example.tw_thainguyen.service;

import com.example.tw_thainguyen.model.dto.UserLoginRequestDTO;
import com.example.tw_thainguyen.model.dto.UserLoginResponseDTO;

public interface AuthService {
    UserLoginResponseDTO login(UserLoginRequestDTO loginRequest);
}

