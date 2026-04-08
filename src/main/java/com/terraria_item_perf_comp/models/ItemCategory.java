package com.terraria_item_perf_comp.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "item_category")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "title_id",
            foreignKey = @ForeignKey(name = "fk_item_category_title"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Title title;

    @Column(columnDefinition = "text")
    private String imgUrl;
}
