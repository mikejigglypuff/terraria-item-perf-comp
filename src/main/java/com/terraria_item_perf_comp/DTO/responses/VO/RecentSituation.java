package com.terraria_item_perf_comp.DTO.responses.VO;

public record RecentSituation(
        String progression,
        String itemName1,
        int item1Id,
        String itemName2,
        int item2Id,
        String chosenItemName,
        int choseItemId,
        String chosenReason
) {
}
