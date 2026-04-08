package com.terraria_item_perf_comp.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item_comp_games")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ItemCompGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "title_id",
            foreignKey = @ForeignKey(name = "fk_item_comp_games_title"))
    private Title title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",
            foreignKey = @ForeignKey(name = "fk_item_comp_games_user"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",
            foreignKey = @ForeignKey(name = "fk_item_comp_games_category"))
    private ItemCategory category;

    @Column(nullable = false, length = 16)
    @Convert(converter = ItemCompGameStatusConverter.class)
    @Builder.Default
    private ItemCompGameStatus status = ItemCompGameStatus.READY;

    @Column(name = "expected_rounds", nullable = false)
    private int expectedRounds;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "voted_rounds", nullable = false)
    @Builder.Default
    private int votedRounds = 0;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void abandon() {
        this.status = ItemCompGameStatus.ABANDONED;
    }

    public void activateIfReady() {
        if (this.status == ItemCompGameStatus.READY) {
            this.status = ItemCompGameStatus.ACTIVE;
        }
    }

    public void registerVoteRound() {
        this.votedRounds++;
    }

    public void completeIfFinished() {
        if (this.votedRounds == this.expectedRounds) {
            this.status = ItemCompGameStatus.COMPLETED;
            this.completedAt = LocalDateTime.now();
        }
    }
}

