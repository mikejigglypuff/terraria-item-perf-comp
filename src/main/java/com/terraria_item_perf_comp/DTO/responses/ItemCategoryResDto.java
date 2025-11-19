package com.terraria_item_perf_comp.DTO.responses;

import com.terraria_item_perf_comp.models.ItemCategory;

import java.util.List;

public record ItemCategoryResDto(
        String message,
        List<ItemCategory> items
) {
}
