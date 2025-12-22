package com.example.tw_thainguyen.controller;

import com.example.tw_thainguyen.model.dto.BaseResponse;
import com.example.tw_thainguyen.model.dto.UserLoginRequestDTO;
import com.example.tw_thainguyen.model.dto.UserLoginResponseDTO;
import com.example.tw_thainguyen.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    
    private final AuthService authService;
    
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<UserLoginResponseDTO>> login(@Valid @RequestBody UserLoginRequestDTO loginRequest) {
        UserLoginResponseDTO data = authService.login(loginRequest);
        return ResponseEntity.ok(BaseResponse.success(data, "Đăng nhập thành công"));
    }
}

