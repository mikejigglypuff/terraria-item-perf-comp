package com.terraria_item_perf_comp.DTO.responses;

import java.util.List;

import com.terraria_item_perf_comp.DTO.responses.VO.ItemSelectionRate;

public record BalanceChooseResDto(
        String message,
        List<ItemSelectionRate> itemSelectionRates,
        List<String> item1Reasons,
        List<String> item2Reasons
) {
}
