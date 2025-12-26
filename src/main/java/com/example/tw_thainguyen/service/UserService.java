package com.example.tw_thainguyen.service;

import com.example.tw_thainguyen.model.dto.PageResponse;
import com.example.tw_thainguyen.model.dto.UserCreateAccountDTO;
import com.example.tw_thainguyen.model.dto.UserResponseDTO;
import com.example.tw_thainguyen.model.dto.UserUpdateDTO;
import com.example.tw_thainguyen.model.entity.User;
import org.springframework.data.domain.Pageable;

public interface UserService extends BaseService<User, Long, UserCreateAccountDTO, UserUpdateDTO, UserResponseDTO> {
    UserResponseDTO lockUser(Long id);

    UserResponseDTO unlockUser(Long id);
    
    /**
     * Lấy danh sách người dùng với phân trang
     */
    PageResponse<UserResponseDTO> findAllPaginated(Pageable pageable);
    
    /**
     * Tìm kiếm và lấy danh sách người dùng với phân trang
     * @param keyword Từ khóa tìm kiếm (tìm trong username, email, fullname, phone)
     * @param pageable Thông tin phân trang và sắp xếp
     */
    PageResponse<UserResponseDTO> searchUsers(String keyword, Pageable pageable);
    
    /**
     * Lấy danh sách người dùng với phân trang, sắp xếp và tìm kiếm
     * Xử lý tất cả logic nghiệp vụ: validation, tạo Pageable, quyết định search hay findAll
     * 
     * @param page Số trang (bắt đầu từ 0)
     * @param size Số lượng items mỗi trang
     * @param sortBy Trường sắp xếp (mặc định: userId)
     * @param sortDir Hướng sắp xếp: ASC hoặc DESC (mặc định: ASC)
     * @param search Từ khóa tìm kiếm (tùy chọn)
     * @return PageResponse chứa danh sách người dùng và thông tin phân trang
     */
    PageResponse<UserResponseDTO> getAllUsers(Integer page, Integer size, String sortBy, String sortDir, String search);
}

