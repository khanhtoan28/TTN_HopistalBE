package com.example.tw_thainguyen.service;

import com.example.tw_thainguyen.model.dto.UserCreateAccountDTO;
import com.example.tw_thainguyen.model.dto.UserResponseDTO;
import com.example.tw_thainguyen.model.dto.UserUpdateDTO;
import com.example.tw_thainguyen.model.entity.User;

public interface UserService extends BaseService<User, Long, UserCreateAccountDTO, UserUpdateDTO, UserResponseDTO> {
    UserResponseDTO lockUser(Long id);
    UserResponseDTO unlockUser(Long id);
}

