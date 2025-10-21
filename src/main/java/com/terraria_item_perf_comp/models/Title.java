package com.terraria_item_perf_comp.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "titles")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Title {
    @Id
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "text")
    private String imgUrl;
}