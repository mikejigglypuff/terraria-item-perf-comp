package com.terraria_item_perf_comp.models;

import com.terraria_item_perf_comp.models.embeddable.ItemCompStatsId;
import jakarta.persistence.*;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "item_comp_stats")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ItemCompStat {
    @EmbeddedId
    private ItemCompStatsId id;

    @MapsId("situationId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "situation_id",
            foreignKey = @ForeignKey(name = "fk_item_comp_stats_situation"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ItemCompSituation situation;

    @MapsId("itemId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id",
            foreignKey = @ForeignKey(name = "fk_item_comp_stats_item"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Item item;

    @ColumnDefault("0")
    private Long voteCount;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
