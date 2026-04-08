package com.terraria_item_perf_comp.DTO.responses;

import com.terraria_item_perf_comp.DTO.responses.VO.ProgressionDto;
import com.terraria_item_perf_comp.repository.projections.ItemPair;

import java.util.List;

public record GameStartResDto(
        String message,
        ProgressionDto progressionDto,
        List<ItemPair> itemPair,
        Integer balanceGameId,
        Integer compGameId
) {
}
