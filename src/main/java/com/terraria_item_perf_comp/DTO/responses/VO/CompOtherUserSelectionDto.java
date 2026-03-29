package com.terraria_item_perf_comp.DTO.responses.VO;

import java.time.LocalDateTime;

public record CompOtherUserSelectionDto(
        int chosenItemId,
        String chooseReason,
        LocalDateTime createdAt,
        int progressionId,
        int item1Id,
        int item2Id
) {
}
