package com.terraria_item_perf_comp.DTO.requests;

public record CompChooseReqDto(
        int titleId,
        int categoryId,
        int chosenId,
        int notChosenId,
        int progressionId,
        String chooseReason
) {
}
