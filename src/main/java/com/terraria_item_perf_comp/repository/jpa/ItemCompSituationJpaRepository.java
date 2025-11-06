package com.terraria_item_perf_comp.repository.jpa;

import com.terraria_item_perf_comp.models.ItemCompSituation;
import com.terraria_item_perf_comp.repository.ItemCompSituationRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

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
}