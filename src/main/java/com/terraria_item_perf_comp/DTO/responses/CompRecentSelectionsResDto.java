package com.terraria_item_perf_comp.DTO.responses;

import java.util.List;

import com.terraria_item_perf_comp.DTO.responses.VO.CompOtherUserSelectionDto;

public record CompRecentSelectionsResDto(
        String message,
        List<CompOtherUserSelectionDto> selections
) {
}
