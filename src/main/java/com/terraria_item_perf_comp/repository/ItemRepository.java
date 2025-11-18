package com.terraria_item_perf_comp.repository;

import java.util.List;

import com.terraria_item_perf_comp.repository.projections.ItemPair;

public interface ItemRepository {
    List<ItemPair> findUnseenItemPairs(int titleId, int categoryId, int progressionId);
}


