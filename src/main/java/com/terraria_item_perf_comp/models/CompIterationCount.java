package com.terraria_item_perf_comp.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "comp_iteration_counts")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompIterationCount {
    @Id
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",
            foreignKey = @ForeignKey(name = "fk_comp_iteration_counts_category"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ItemCategory category;

    @ColumnDefault("0")
    private int iterationCount;
}
