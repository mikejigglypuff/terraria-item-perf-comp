package com.terraria_item_perf_comp.models;

import jakarta.persistence.*;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "item_balance_votes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ItemBalanceVote {
    @Id
    private int id;

    @ColumnDefault("0")
    private int chooseCount;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "situation_id",
            foreignKey = @ForeignKey(name = "fk_item_balance_votes_situations"))
    private ItemCompSituation situation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",
            foreignKey = @ForeignKey(name = "fk_item_balance_votes_user"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chosen_item_id",
            foreignKey = @ForeignKey(name = "fk_item_balance_votes_chosen_item"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Item chosenItem;
}
