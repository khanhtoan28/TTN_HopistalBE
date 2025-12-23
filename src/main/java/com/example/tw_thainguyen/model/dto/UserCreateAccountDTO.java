package com.example.tw_thainguyen.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserCreateAccountDTO {

    @NotBlank(message = "Tên người dùng không được để trống")
    @Size(min = 6 , max = 100, message = "Tên người dùng phải từ 6 - 100 kí tự")
    @Pattern(regexp = "^[a-zA-z0-9]+$", message = "Tên người dùng chỉ được chứa chữ cái và số")
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6 , max = 100, message = "Mật khẩu phải từ 6 - 100 kí tự")
    private String password;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Số điện thoại không đúng định dạng")
    private String phone;

    @NotBlank(message = "Họ và tên không được để trống")
    @Size(min = 6 , max = 100, message = "Họ và tên phải từ 6 - 100 kí tự")
    private String fullname;

    private String avatar;

}
