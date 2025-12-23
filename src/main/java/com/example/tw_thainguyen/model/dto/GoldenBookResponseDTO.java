package com.example.tw_thainguyen.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GoldenBookResponseDTO {
    private Long goldenBookId;
    private String goldenBookName;
    private String level;
    private Integer year;
    private String department;
    private String image;
    private String description;
}
