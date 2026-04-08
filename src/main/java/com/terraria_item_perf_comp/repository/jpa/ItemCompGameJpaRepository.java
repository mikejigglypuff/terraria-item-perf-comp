package com.terraria_item_perf_comp.repository.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.terraria_item_perf_comp.models.ItemCompGame;
import com.terraria_item_perf_comp.models.ItemCompGameStatus;

import jakarta.persistence.LockModeType;

@Repository
public interface ItemCompGameJpaRepository extends JpaRepository<ItemCompGame, Integer> {

    Optional<ItemCompGame> findFirstByUser_IdOrderByCreatedAtDesc(int userId);

    Optional<ItemCompGame> findFirstByUser_IdAndTitle_IdAndCategory_IdAndStatusInOrderByCreatedAtDesc(
            int userId,
            int titleId,
            int categoryId,
            java.util.Collection<ItemCompGameStatus> statuses
    );

    Optional<ItemCompGame> findFirstByUser_IdAndTitle_IdAndExpectedRoundsAndCategory_IdAndStatusOrderByCreatedAtDesc(
            int userId,
            int titleId,
            int expectedRounds,
            int categoryId,
            ItemCompGameStatus status
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select g from ItemCompGame g where g.id = :id")
    Optional<ItemCompGame> findByIdForUpdate(@Param("id") int id);
}

