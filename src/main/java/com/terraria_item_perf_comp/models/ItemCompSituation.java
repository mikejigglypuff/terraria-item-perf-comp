package com.terraria_item_perf_comp.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(
        name = "item_comp_situations",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_situation",
                        columnNames = {"progression_id","category_id","comp_item_max","comp_item_min"}
                )
        }
)
@Check(constraints = "comp_item_1 <> comp_item_2")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemCompSituation {
    @Id
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "title_id",
            foreignKey = @ForeignKey(name = "fk_item_comp_situations_title"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Title title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "progression_id",
            foreignKey = @ForeignKey(name = "fk_item_comp_situations_progression"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Progression progression;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",
            foreignKey = @ForeignKey(name = "fk_item_comp_situations_category"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ItemCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comp_item_1",
            foreignKey = @ForeignKey(name = "fk_item_comp_situations_item1"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Item compItem1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comp_item_2",
            foreignKey = @ForeignKey(name = "fk_item_comp_situations_item2"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Item compItem2;

    // MySQL 가상 컬럼의 읽기 전용 매핑
    @Column(insertable = false, updatable = false)
    private Integer compItemMax;

    @Column(insertable = false, updatable = false)
    private Integer compItemMin;
}
