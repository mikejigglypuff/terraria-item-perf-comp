package com.terraria_item_perf_comp.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.terraria_item_perf_comp.models.ItemCompVote;
import com.terraria_item_perf_comp.repository.ItemCompVoteRepository;
import com.terraria_item_perf_comp.repository.projections.CompRecentVoteRow;

@Repository
public interface ItemCompVoteJpaRepository extends CrudRepository<ItemCompVote, Integer>, ItemCompVoteRepository {

  @Override
  @Modifying
  @Transactional
  @Query(value = "INSERT INTO item_comp_votes (situation_id, user_id, chosen_item_id, vote_count, comp_count, choose_reason)\n" +
          "VALUES (:situationId, :userId, :chosenItemId, :voteCount, :compCount, :chooseReason)\n" +
          "ON DUPLICATE KEY UPDATE\n" +
          "  comp_count = VALUES(comp_count),\n" +
          "  choose_reason = VALUES(choose_reason),\n" +
          "  updated_at = CURRENT_TIMESTAMP",
          nativeQuery = true)
  int upsertVote(
          @Param("situationId") int situationId,
          @Param("userId") int userId,
          @Param("chosenItemId") int chosenItemId,
          @Param("voteCount") int voteCount,
          @Param("compCount") short compCount,
          @Param("chooseReason") String chooseReason
  );

  @Override
  @Query(value = "SELECT choose_reason FROM item_comp_votes " +
          "WHERE chosen_item_id = :itemId AND choose_reason IS NOT NULL " +
          "ORDER BY created_at DESC LIMIT 5",
          nativeQuery = true)
  List<String> findTop5ReasonsByItemIdOrderByCreatedAtDesc(@Param("itemId") int itemId);

  @Override
  @Query(value = "SELECT v.chosen_item_id AS chosenItemId, v.choose_reason AS chooseReason, v.created_at AS createdAt, "
          + "s.progression_id AS progressionId, s.comp_item_1 AS item1Id, s.comp_item_2 AS item2Id "
          + "FROM item_comp_votes v "
          + "INNER JOIN item_comp_situations s ON v.situation_id = s.id "
          + "WHERE s.title_id = :titleId AND s.category_id = :categoryId "
          + "AND (:excludeUserId IS NULL OR v.user_id <> :excludeUserId) "
          + "ORDER BY v.created_at DESC "
          + "LIMIT :limit",
          nativeQuery = true)
  List<CompRecentVoteRow> findRecentSelectionsByTitleAndCategoryExcludingUser(
          @Param("titleId") int titleId,
          @Param("categoryId") int categoryId,
          @Param("excludeUserId") Integer excludeUserId,
          @Param("limit") int limit
  );

}


