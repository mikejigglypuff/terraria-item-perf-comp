package com.terraria_item_perf_comp.DTO.responses;

import com.terraria_item_perf_comp.DTO.responses.VO.BalanceStat;
import com.terraria_item_perf_comp.DTO.responses.VO.CompStat;
import com.terraria_item_perf_comp.DTO.responses.VO.RecentSituation;

public record UserStatResDto(
        String message,
        CompStat comp,
        BalanceStat balance,
        RecentSituation recents
) {
}
