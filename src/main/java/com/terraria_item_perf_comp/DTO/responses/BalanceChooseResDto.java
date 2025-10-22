package com.terraria_item_perf_comp.DTO.responses;

public record BalanceChooseResDto(
        String message,
        float item1Rate,
        float item2Rate,
        String[] item1Reasons,
        String[] item2Reasons
) {
}
