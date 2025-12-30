package com.example.tw_thainguyen.service.impl;

import com.example.tw_thainguyen.exception.ResourceAlreadyExistsException;
import com.example.tw_thainguyen.exception.ResourceNotFoundException;
import com.example.tw_thainguyen.model.dto.PageResponse;
import com.example.tw_thainguyen.model.dto.UserCreateAccountDTO;
import com.example.tw_thainguyen.model.dto.UserResponseDTO;
import com.example.tw_thainguyen.model.dto.UserUpdateDTO;
import com.example.tw_thainguyen.model.entity.User;
import com.example.tw_thainguyen.repository.UserRepository;
import com.example.tw_thainguyen.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
    
@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long, UserCreateAccountDTO, UserUpdateDTO, UserResponseDTO>
        implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        super(userRepository);
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected UserResponseDTO toResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .fullname(user.getFullname())
                .avatar(user.getAvatar())
                .isLocked(user.getIsLocked())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    @Override
    protected User toEntity(UserCreateAccountDTO createDTO) {
        return User.builder()
                .username(createDTO.getUsername())
                .password(createDTO.getPassword())
                .email(createDTO.getEmail())
                .phone(createDTO.getPhone())
                .fullname(createDTO.getFullname())
                .avatar(createDTO.getAvatar())
                .isLocked(false)
                .build();
    }

    @Override
    protected void updateEntity(User user, UserUpdateDTO updateDTO) {
        if (updateDTO.getFullname() != null) {
            user.setFullname(updateDTO.getFullname());
        }
        if (updateDTO.getEmail() != null) {
            user.setEmail(updateDTO.getEmail());
        }
        if (updateDTO.getPhone() != null) {
            user.setPhone(updateDTO.getPhone());
        }
        if (updateDTO.getAvatar() != null) {
            user.setAvatar(updateDTO.getAvatar());
        }
    }

    @Override
    @Transactional
    public UserResponseDTO create(UserCreateAccountDTO createDTO) {
        // Kiểm tra username đã tồn tại
        if (userRepository.existsByUsername(createDTO.getUsername())) {
            throw new ResourceAlreadyExistsException("Người dùng", "tên đăng nhập", createDTO.getUsername());
        }

        // Kiểm tra email đã tồn tại
        if (createDTO.getEmail() != null && userRepository.existsByEmail(createDTO.getEmail())) {
            throw new ResourceAlreadyExistsException("Người dùng", "email", createDTO.getEmail());
        }

        // Kiểm tra phone đã tồn tại
        if (createDTO.getPhone() != null && userRepository.existsByPhone(createDTO.getPhone())) {
            throw new ResourceAlreadyExistsException("Người dùng", "số điện thoại", createDTO.getPhone());
        }

        User user = toEntity(createDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return toResponseDTO(savedUser);
    }

    @Override
    @Transactional
    public UserResponseDTO update(Long id, UserUpdateDTO updateDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng", "id", id));

        // Kiểm tra email đã tồn tại (nếu thay đổi)
        if (updateDTO.getEmail() != null && !updateDTO.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(updateDTO.getEmail())) {
                throw new ResourceAlreadyExistsException("Người dùng", "email", updateDTO.getEmail());
            }
        }

        // Kiểm tra phone đã tồn tại (nếu thay đổi)
        if (updateDTO.getPhone() != null && !updateDTO.getPhone().equals(user.getPhone())) {
            if (userRepository.existsByPhone(updateDTO.getPhone())) {
                throw new ResourceAlreadyExistsException("Người dùng", "số điện thoại", updateDTO.getPhone());
            }
        }

        updateEntity(user, updateDTO);
        user.setUpdatedAt(LocalDateTime.now());
        User updatedUser = userRepository.save(user);
        return toResponseDTO(updatedUser);
    }

    @Override
    @Transactional
    public UserResponseDTO lockUser(Long id) {
        return toggleUserLockStatus(id, true);
    }

    @Override
    @Transactional
    public UserResponseDTO unlockUser(Long id) {
        return toggleUserLockStatus(id, false);
    }
    
    /**
     * Toggle user lock status
     */
    private UserResponseDTO toggleUserLockStatus(Long id, boolean isLocked) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng", "id", id));
        user.setIsLocked(isLocked);
        user.setUpdatedAt(LocalDateTime.now());
        return toResponseDTO(userRepository.save(user));
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResponse<UserResponseDTO> findAllPaginated(Pageable pageable) {
        org.springframework.data.domain.Page<UserResponseDTO> page = super.findAll(pageable);
        return PageResponse.from(page);
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResponse<UserResponseDTO> searchUsers(String keyword, Pageable pageable) {
        org.springframework.data.domain.Page<User> userPage = userRepository.searchUsers(keyword, pageable);
        org.springframework.data.domain.Page<UserResponseDTO> dtoPage = userPage.map(this::toResponseDTO);
        return PageResponse.from(dtoPage);
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResponse<UserResponseDTO> getAllUsers(Integer page, Integer size, String sortBy, String sortDir, String search) {
        if (page == null && size == null) {
            return null;
        }
        
        int pageNumber = Math.max(0, page != null ? page : 0);
        int pageSize = size != null ? Math.min(Math.max(1, size), 100) : 10;
        String normalizedSortBy = (sortBy == null || sortBy.trim().isEmpty()) ? "userId" : sortBy;
        String normalizedSortDir = normalizeSortDir(sortDir);
        
        Sort sort = Sort.by(Sort.Direction.fromString(normalizedSortDir), normalizedSortBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        
        return (search != null && !search.trim().isEmpty()) 
                ? searchUsers(search.trim(), pageable) 
                : findAllPaginated(pageable);
    }
    
    private String normalizeSortDir(String sortDir) {
        if (sortDir == null || sortDir.trim().isEmpty()) {
            return "ASC";
        }
        String upper = sortDir.toUpperCase();
        return (upper.equals("ASC") || upper.equals("DESC")) ? upper : "ASC";
    }
}

