package com.example.tw_thainguyen.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GoldenBookRequestDTO {

    @NotBlank(message = "Tên sách vàng không được để trống")
    @Size(min = 1, max = 100, message = "Tên sách vàng phải từ 1 - 100 kí tự")
    private String goldenBookName;

    @NotBlank(message = "Cấp độ không được để trống")
    @Size(min = 1, max = 100, message = "Cấp độ phải từ 1 - 100 kí tự")
    private String level;

    @NotNull(message = "Năm không được để trống")
    @Min(value = 1900, message = "Năm phải từ 1900 trở lên")
    @Max(value = 2100, message = "Năm không được vượt quá 2100")
    private Integer year;

    @NotBlank(message = "Khoa/phòng ban không được để trống")
    @Size(min = 1, max = 100, message = "Khoa/phòng ban phải từ 1 - 100 kí tự")
    private String department;

    @Size(max = 255, message = "URL hình ảnh không được vượt quá 255 kí tự")
    private String image;
    
    private Long imageId;

    @Size(max = 500, message = "Mô tả không được vượt quá 500 kí tự")
    private String description;
}
