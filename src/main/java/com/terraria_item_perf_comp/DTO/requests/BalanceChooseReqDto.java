package com.terraria_item_perf_comp.DTO.requests;

public record BalanceChooseReqDto(
        int gameId,
        int titleId,
        int categoryId,
        int chosenId,
        int notChosenId,
        int progressionId,
        int item1Id,
        int item2Id
) {
}
