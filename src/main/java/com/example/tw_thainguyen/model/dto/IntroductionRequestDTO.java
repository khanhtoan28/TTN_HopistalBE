package com.example.tw_thainguyen.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class IntroductionRequestDTO {

    @NotBlank(message = "Phần không được để trống")
    @Size(min = 1, max = 100, message = "Phần phải từ 1 - 100 kí tự")
    private String section;

    @NotBlank(message = "Nội dung không được để trống")
    @Size(min = 1, max = 10000, message = "Nội dung phải từ 1 - 10000 kí tự")
    private String content;
}
