package com.terraria_item_perf_comp.repository.jpa;

import com.terraria_item_perf_comp.models.ItemCompVote;
import com.terraria_item_perf_comp.repository.ItemCompVoteRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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
    
}


