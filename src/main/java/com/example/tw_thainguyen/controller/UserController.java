package com.example.tw_thainguyen.controller;

import com.example.tw_thainguyen.model.dto.BaseResponse;
import com.example.tw_thainguyen.model.dto.UserCreateAccountDTO;
import com.example.tw_thainguyen.model.dto.UserResponseDTO;
import com.example.tw_thainguyen.model.dto.UserUpdateDTO;
import com.example.tw_thainguyen.model.entity.User;
import com.example.tw_thainguyen.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController extends BaseController<User, Long, UserCreateAccountDTO, UserUpdateDTO, UserResponseDTO> {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        super(userService);
        this.userService = userService;
    }
    
    @GetMapping
    public ResponseEntity<BaseResponse<java.util.List<UserResponseDTO>>> getAll() {
        return super.getAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<UserResponseDTO>> getById(@PathVariable Long id) {
        return super.getById(id);
    }
    
    @PostMapping
    public ResponseEntity<BaseResponse<UserResponseDTO>> create(@Valid @RequestBody UserCreateAccountDTO createDTO) {
        return super.create(createDTO);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<UserResponseDTO>> update(@PathVariable Long id,
                                                                 @Valid @RequestBody UserUpdateDTO updateDTO) {
        return super.update(id, updateDTO);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long id) {
        return super.delete(id);
    }
    
    @PutMapping("/{id}/lock")
    public ResponseEntity<BaseResponse<UserResponseDTO>> lockUser(@PathVariable Long id) {
        UserResponseDTO data = userService.lockUser(id);
        return ResponseEntity.ok(BaseResponse.success(data, "User locked successfully"));
    }
    
    @PutMapping("/{id}/unlock")
    public ResponseEntity<BaseResponse<UserResponseDTO>> unlockUser(@PathVariable Long id) {
        UserResponseDTO data = userService.unlockUser(id);
        return ResponseEntity.ok(BaseResponse.success(data, "User unlocked successfully"));
    }
}

