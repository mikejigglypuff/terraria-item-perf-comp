package com.terraria_item_perf_comp.DTO.responses;

import com.terraria_item_perf_comp.models.Item;
import com.terraria_item_perf_comp.models.Progression;

public record GameStartResDto(
        String message,
        Progression progression,
        Item item1,
        Item item2
) {
}
