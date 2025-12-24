package com.example.tw_thainguyen.model.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "artifacts")
public class Artifacts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artifact_id", nullable = false)
    private Long artifactId;

    @Column(name = "name", nullable = false, length = 255)
    private String artifactName;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "period", length = 255)
    private String period;

    @Column(name = "type", length = 255)    
    private String type;

    @Column(name = "space", length = 255)
    private String space;
}
