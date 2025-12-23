package com.example.tw_thainguyen.model.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Setter
@Builder
@Table(name = "golden_book")
public class GoldenBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "golden_book_id", nullable = false)
    private Long goldenBookId;

    @Column(name = "golden_book_name", nullable = false, length = 100)
    private String goldenBookName;

    @Column(name = "level", nullable = false, length = 100)
    private String level;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "department, nullable = false, length = 100")
    private String department;

    @Column(name = "image", length = 255)
    private String image;

    @Column(name = "description", length = 500)
    private String description;
}
