package com.example.tw_thainguyen.security;

import com.example.tw_thainguyen.model.entity.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class UserPrinciple implements UserDetails {
    //Lớp UserPrinciple đóng vai trò làm lớp đại diện cho người dùng khi Spring Security xác thực.
    // Cung cấp các thông tin của người dùng như tài khoản mật khẩu và trạng thái tài khoản
    // Ngoài ra thì nó cung cấp quyền
    // Này để cung cấp thông tin cho security xác thực
    private User user;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.getIsLocked();
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return !user.getIsDeleted();
    }
}
