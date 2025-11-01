package com.terraria_item_perf_comp.DTO.responses;

import com.terraria_item_perf_comp.models.ItemCategory;

public record ItemCategoryResDto(
        String message,
        ItemCategory[] items
) {
}
