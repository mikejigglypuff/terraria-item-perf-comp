package com.terraria_item_perf_comp.repository;

import com.terraria_item_perf_comp.models.ItemCategory;

import java.util.List;

/**
 * Domain-level repository interface for ItemCategory.
 * Exposes queries without coupling to JPA specifics.
 */
public interface ItemCategoryRepository {
    List<ItemCategory> findByTitleId(int titleId);
}


