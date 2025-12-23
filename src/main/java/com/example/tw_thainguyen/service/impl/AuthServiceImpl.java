package com.example.tw_thainguyen.service.impl;

import com.example.tw_thainguyen.exception.BadRequestException;
import com.example.tw_thainguyen.model.dto.UserLoginRequestDTO;
import com.example.tw_thainguyen.model.dto.UserLoginResponseDTO;
import com.example.tw_thainguyen.security.UserPrinciple;
import com.example.tw_thainguyen.security.jwt.JwtProvider;
import com.example.tw_thainguyen.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @Override
    @Transactional(readOnly = true)
    public UserLoginResponseDTO login(UserLoginRequestDTO loginRequest) {
        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Get user details
            UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

            // Check if account is locked
            if (userPrinciple.getUser().getIsLocked()) {
                throw new BadRequestException("Tài khoản đã bị khóa");
            }

            // Check if account is deleted
            if (userPrinciple.getUser().getIsDeleted()) {
                throw new BadRequestException("Tài khoản đã bị xóa");
            }

            // Generate JWT token
            String token = jwtProvider.generateToken(userPrinciple);

            // Build response
            return UserLoginResponseDTO.builder()
                    .userId(userPrinciple.getUser().getUserId())
                    .username(userPrinciple.getUsername())
                    .typeToken("Bearer")
                    .accessToken(token)
                    .build();

        } catch (BadCredentialsException e) {
            throw new BadRequestException("Tên đăng nhập hoặc mật khẩu không đúng");
        } catch (Exception e) {
            throw new BadRequestException("Đăng nhập thất bại: " + e.getMessage());
        }
    }
}

