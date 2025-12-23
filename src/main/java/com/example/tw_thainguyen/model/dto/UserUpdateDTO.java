package com.example.tw_thainguyen.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserUpdateDTO {

    @Size(min = 6, max = 100, message = "Họ và tên phải từ 6 - 100 kí tự")
    private String fullname;

    @Email(message = "Email không đúng định dạng")
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Số điện thoại không đúng định dạng")
    private String phone;

    private String avatar;
}

