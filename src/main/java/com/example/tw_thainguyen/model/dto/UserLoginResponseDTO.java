package com.example.tw_thainguyen.model.dto;

import lombok.Builder;
import lombok.Setter;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class UserLoginResponseDTO {
    private Long userId;
    private String username;
    private String typeToken;
    private String accessToken;
}
