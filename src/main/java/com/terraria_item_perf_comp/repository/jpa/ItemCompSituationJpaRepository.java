package com.terraria_item_perf_comp.repository.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.terraria_item_perf_comp.models.ItemCompSituation;
import com.terraria_item_perf_comp.repository.ItemCompSituationRepository;

@Repository
public interface ItemCompSituationJpaRepository extends CrudRepository<ItemCompSituation, Integer>, ItemCompSituationRepository {
    @Override
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO item_comp_situations (title_id, progression_id, category_id, comp_item_1, comp_item_2)\n" +
            "VALUES (:titleId, :progressionId, :categoryId, :chosenId, :notChosenId)",
            nativeQuery = true)
    int insertByIds(
            @Param("titleId") int titleId,
            @Param("progressionId") int progressionId,
            @Param("categoryId") int categoryId,
            @Param("chosenId") int chosenId,
            @Param("notChosenId") int notChosenId
    );

    @Override
    @Query(value = "SELECT id FROM item_comp_situations\n" +
            "WHERE title_id = :titleId\n" +
            "  AND progression_id = :progressionId\n" +
            "  AND category_id = :categoryId\n" +
            "  AND comp_item_min = LEAST(:item1Id, :item2Id)\n" +
            "  AND comp_item_max = GREATEST(:item1Id, :item2Id)\n" +
            "LIMIT 1",
            nativeQuery = true)
    Optional<Integer> findSituationId(
            @Param("titleId") int titleId,
            @Param("progressionId") int progressionId,
            @Param("categoryId") int categoryId,
            @Param("item1Id") int item1Id,
            @Param("item2Id") int item2Id
    );
}