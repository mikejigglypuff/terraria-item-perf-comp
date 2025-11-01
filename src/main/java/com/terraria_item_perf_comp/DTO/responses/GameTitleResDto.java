package com.terraria_item_perf_comp.DTO.responses;

import com.terraria_item_perf_comp.models.Title;

public record GameTitleResDto(
        String message,
        Title[] items
) {
}
