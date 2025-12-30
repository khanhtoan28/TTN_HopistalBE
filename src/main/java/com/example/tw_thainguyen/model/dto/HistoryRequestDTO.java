package com.example.tw_thainguyen.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class HistoryRequestDTO {

    @NotBlank(message = "Năm không được để trống")
    @Size(min = 1, max = 255, message = "Năm phải từ 1 - 255 kí tự")
    private String year;

    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(min = 1, max = 255, message = "Tiêu đề phải từ 1 - 255 kí tự")
    private String title;

    @NotBlank(message = "Giai đoạn không được để trống")
    @Size(min = 1, max = 255, message = "Giai đoạn phải từ 1 - 255 kí tự")
    private String period;

    @NotBlank(message = "Mô tả không được để trống")
    @Size(min = 1, max = 1000, message = "Mô tả phải từ 1 - 1000 kí tự")
    private String description;

    @Size(max = 255, message = "URL icon không được vượt quá 255 kí tự")
    private String icon;

    private Long iconImageId;

    @Size(max = 255, message = "URL hình ảnh không được vượt quá 255 kí tự")
    private String image;
    
    private Long imageId;
}
