package com.terraria_item_perf_comp.DTO.responses;

import com.terraria_item_perf_comp.models.Title;
import java.util.List;

public record GameTitleResDto(
        String message,
        List<Title> items
) {
}
