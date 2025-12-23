package com.example.tw_thainguyen.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class HistoryResponseDTO {
    private Long historyId;
    private String year;
    private String title;
    private String period;
    private String description;
    private String icon;
    private String image;
}
