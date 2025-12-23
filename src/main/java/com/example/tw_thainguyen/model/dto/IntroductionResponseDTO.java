package com.example.tw_thainguyen.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class IntroductionResponseDTO {
    private Long introductionId;
    private String section;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
