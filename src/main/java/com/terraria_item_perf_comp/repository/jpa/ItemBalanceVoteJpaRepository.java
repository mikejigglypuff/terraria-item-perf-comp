package com.terraria_item_perf_comp.repository.jpa;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.terraria_item_perf_comp.models.ItemBalanceVote;
import com.terraria_item_perf_comp.repository.ItemBalanceVoteRepository;

@Repository
public interface ItemBalanceVoteJpaRepository extends CrudRepository<ItemBalanceVote, Integer>, ItemBalanceVoteRepository {

    @Override
    @Modifying
    @Transactional
    @Query(value = """
            INSERT INTO item_balance_votes (situation_id, game_id, user_id, chosen_item_id, choose_count)
            VALUES (:situationId, :gameId, :userId, :chosenItemId, 1)
            ON DUPLICATE KEY UPDATE
              chosen_item_id = VALUES(chosen_item_id),
              choose_count = choose_count + 1,
              updated_at = CURRENT_TIMESTAMP
            """,
            nativeQuery = true)
    int upsertBalanceVote(
            @Param("situationId") int situationId,
            @Param("gameId") int gameId,
            @Param("userId") int userId,
            @Param("chosenItemId") int chosenItemId
    );

    @Override
    @Query(value = """
            SELECT IFNULL(CAST(v.choose_count AS DOUBLE) / NULLIF(t.total_count, 0), 0)
            FROM item_balance_votes v
            JOIN (
              SELECT situation_id, user_id, game_id, SUM(choose_count) AS total_count
              FROM item_balance_votes
              WHERE situation_id = :situationId AND user_id = :userId AND game_id = :gameId
              GROUP BY situation_id, user_id, game_id
            ) t
              ON v.situation_id = t.situation_id
             AND v.user_id = t.user_id
             AND v.game_id = t.game_id
            WHERE v.situation_id = :situationId
              AND v.user_id = :userId
              AND v.game_id = :gameId
              AND v.chosen_item_id = :chosenItemId
            """,
            nativeQuery = true)
    Double findSelectionRate(
            @Param("situationId") int situationId,
            @Param("gameId") int gameId,
            @Param("userId") int userId,
            @Param("chosenItemId") int chosenItemId
    );
}

