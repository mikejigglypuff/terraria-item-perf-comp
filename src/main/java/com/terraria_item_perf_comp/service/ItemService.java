package com.terraria_item_perf_comp.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.terraria_item_perf_comp.repository.ItemRepository;
import com.terraria_item_perf_comp.repository.projections.ItemPair;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ProgressionService progressionService;

    public ItemService(ItemRepository itemRepository, ProgressionService progressionService) {
        this.itemRepository = itemRepository;
        this.progressionService = progressionService;
    }

    public List<ItemPair> getUnseenItemPairs(int titleId, int categoryId) {
        int progressionId = progressionService.getRandomProgressionId();
        if (progressionId < 1) {
            return Collections.emptyList();
        }

        return itemRepository.findUnseenItemPairs(titleId, categoryId, progressionId);
    }
}


