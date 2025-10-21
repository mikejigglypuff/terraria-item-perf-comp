package com.terraria_item_perf_comp.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "progressions")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Progression {
    @Id
    private int id;

    @Column(nullable = false)
    private String progressName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "title_id",
            foreignKey = @ForeignKey(name = "fk_progressions_title"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Title title;

    @Column(columnDefinition = "text")
    private String imgUrl;
}