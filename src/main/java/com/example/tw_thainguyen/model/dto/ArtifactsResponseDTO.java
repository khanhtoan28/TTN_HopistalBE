package com.example.tw_thainguyen.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ArtifactsResponseDTO {
    private Long artifactId;
    private String artifactName;
    private String description;
    private String imageUrl;
}
