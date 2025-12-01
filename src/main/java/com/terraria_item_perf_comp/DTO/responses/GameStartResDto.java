package com.terraria_item_perf_comp.DTO.responses;

import com.terraria_item_perf_comp.models.Item;
import com.terraria_item_perf_comp.models.Progression;
import com.terraria_item_perf_comp.repository.projections.ItemPair;

import java.util.List;

public record GameStartResDto(
        String message,
        Progression progression,
        List<ItemPair> itemPair
) {
}
