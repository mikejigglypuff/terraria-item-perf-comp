package com.terraria_item_perf_comp.repository.jpa;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.terraria_item_perf_comp.models.ItemBalanceVote;

@Repository
public interface ItemBalanceVoteJpaRepository extends CrudRepository<ItemBalanceVote, Integer> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO item_balance_votes (situation_id, user_id, chosen_item_id, choose_count)\n" +
            "SELECT :situationId, :userId, :chosenItemId, 1\n" +
            "WHERE NOT EXISTS (\n" +
            "  SELECT 1 FROM item_balance_votes\n" +
            "  WHERE situation_id = :situationId AND user_id = :userId AND chosen_item_id = :chosenItemId\n" +
            ")",
            nativeQuery = true)
    int upsertBalanceVote(
            @Param("situationId") int situationId,
            @Param("userId") int userId,
            @Param("chosenItemId") int chosenItemId
    );

    @Modifying
    @Transactional
    @Query(value = "UPDATE item_balance_votes\n" +
            "SET choose_count = choose_count + 1, updated_at = CURRENT_TIMESTAMP\n" +
            "WHERE situation_id = :situationId AND user_id = :userId AND chosen_item_id = :chosenItemId",
            nativeQuery = true)
    int incrementChooseCount(
            @Param("situationId") int situationId,
            @Param("userId") int userId,
            @Param("chosenItemId") int chosenItemId
    );
}

