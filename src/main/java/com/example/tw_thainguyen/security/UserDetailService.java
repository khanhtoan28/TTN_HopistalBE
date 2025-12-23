package com.example.tw_thainguyen.security;

import com.example.tw_thainguyen.model.entity.User;
import com.example.tw_thainguyen.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng: " + username));
        return UserPrinciple.builder()
                .user(user)
                .authorities(Collections.emptySet())
                .build();
    }
}