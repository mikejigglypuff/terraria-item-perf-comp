package com.terraria_item_perf_comp.DTO.responses;

import java.util.List;

public record BalanceChooseResDto(
        String message,
        float item1Rate,
        float item2Rate,
        List<String> item1Reasons,
        List<String> item2Reasons
) {
}
