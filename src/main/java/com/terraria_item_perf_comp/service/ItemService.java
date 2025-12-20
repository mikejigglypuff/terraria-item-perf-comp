package com.terraria_item_perf_comp.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.terraria_item_perf_comp.repository.ItemRepository;
import com.terraria_item_perf_comp.repository.projections.ItemPair;

@Service
public class ItemService {
    private static final int ITEM_PAIR_LEN = 64;

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<ItemPair> getUnseenItemPairs(int titleId, int categoryId, int progressionId) {
        if (progressionId < 1) {
            return Collections.emptyList();
        }

        return itemRepository.findUnseenItemPairs(titleId, categoryId, progressionId, ITEM_PAIR_LEN);
    }
}


