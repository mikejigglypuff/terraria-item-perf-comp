package com.terraria_item_perf_comp.repository;

public interface ItemCompSituationRepository {
    int insertByIds(int titleId, int progressionId, int categoryId, int chosenId, int notChosenId);
}


