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
@Table(name = "item_comp_votes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ItemCompVote {
    @Id
    private int id;

    @ColumnDefault("0")
    private Short compCount;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(length = 127)
    private String chooseReason;

    // 기존에 지정한 외래키 제약을 그대로 사용
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "situation_id",
            foreignKey = @ForeignKey(name = "fk_item_comp_votes_situation"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ItemCompSituation situation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chosen_item_id",
            foreignKey = @ForeignKey(name = "fk_item_comp_votes_user"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Item chosenItem;

    @ColumnDefault("0")
    private int voteCount;
}