package com.terraria_item_perf_comp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.terraria_item_perf_comp.models.ItemCategory;
import com.terraria_item_perf_comp.repository.ItemCategoryRepository;

@Service
public class ItemCategoryService {

    private final ItemCategoryRepository itemCategoryRepository;

    public ItemCategoryService(ItemCategoryRepository itemCategoryRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
    }

    public List<ItemCategory> getItemCategoriesByTitleId(int titleId) {
        return itemCategoryRepository.findByTitleId(titleId);
    }
}


