package com.example.tw_thainguyen.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ArtifactsRequestDTO {
    @NotBlank(message = "Tên hiện vật không được để trống")
    @Size(min = 1, max = 200, message = "Tên hiện vật phải từ 1 - 200 kí tự")
    private String name;

    @Size(max = 1000, message = "Mô tả hiện vật không được vượt quá 1000 kí tự")
    private String description;

    private String imageUrl;

    @Size(max = 255, message = "Thời kỳ không được vượt quá 255 kí tự")
    private String period;

    @Size(max = 255, message = "Loại hiện vật không được vượt quá 255 kí tự")
    private String type;

    @Size(max = 255, message = "Không gian không được vượt quá 255 kí tự")
    private String space;
}
