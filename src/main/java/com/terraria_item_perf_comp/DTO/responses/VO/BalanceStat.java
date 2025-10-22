package com.terraria_item_perf_comp.DTO.responses.VO;

public record BalanceStat(
        int totalPlays,
        float avgCorrectRate,
        float minCorrectRate,
        float maxCorrectRate
) {
}
