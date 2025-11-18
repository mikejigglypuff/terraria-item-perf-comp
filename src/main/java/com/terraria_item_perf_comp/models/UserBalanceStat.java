package com.terraria_item_perf_comp.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Entity
@Table(name = "user_balance_stats")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBalanceStat {
    @Id
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",
            foreignKey = @ForeignKey(name = "fk_user_balance_stats_user"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "title_id",
            foreignKey = @ForeignKey(name = "fk_user_balance_stats_title"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Title title;
    
    @ColumnDefault("0")
    @Column(name = "correctness_rate", precision = 10, scale = 3)
    private BigDecimal correctnessRate;

    public void updateCorrectnessRate(BigDecimal correctnessRate) {
        this.correctnessRate = correctnessRate;
    }
}
