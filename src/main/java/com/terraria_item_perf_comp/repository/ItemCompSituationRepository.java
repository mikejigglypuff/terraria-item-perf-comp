package com.terraria_item_perf_comp.repository;

import java.util.Optional;

public interface ItemCompSituationRepository {
    int insertByIds(int titleId, int progressionId, int categoryId, int chosenId, int notChosenId);
    
    Optional<Integer> findSituationId(int titleId, int progressionId, int categoryId, int item1Id, int item2Id);
}


