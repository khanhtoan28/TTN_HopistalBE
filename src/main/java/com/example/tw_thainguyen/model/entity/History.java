package com.example.tw_thainguyen.model.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Setter
@Builder
@Table(name = "history")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id", nullable = false)
    private Long historyId;

    @Column(name = "year", nullable = false, length = 255)
    private String year;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "period", nullable = false, length = 255)
    private String period;

    @Column(name = "description ", nullable = false, length = 1000)
    private String description;

    @Column(name = "icon", length = 255)
    private String icon;

    @Column(name = "image", length = 255)
    private String image;
}
