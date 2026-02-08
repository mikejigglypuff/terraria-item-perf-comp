package com.terraria_item_perf_comp.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "items")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    @Id
    private int id;

    @Column(nullable = false)
    private String itemName;

    @Column(columnDefinition = "text")
    private String imgUrl;

    @Column(columnDefinition = "text")
    private String wikiUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "min_progression_id",
            foreignKey = @ForeignKey(name = "fk_items_min_progression"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Progression minProgression;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",
            foreignKey = @ForeignKey(name = "fk_items_category"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ItemCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "title_id",
            foreignKey = @ForeignKey(name = "fk_items_title"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Title title;
}