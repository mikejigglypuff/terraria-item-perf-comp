package com.terraria_item_perf_comp.DTO.responses.VO;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class CompStat {
    private final Map<String, ItemTypeStat> comp = new HashMap<>();

    public void addComp(String categoryName, ItemTypeStat totalPlays) {
        comp.put(categoryName, totalPlays);
    }

    @Getter
    @Setter
    public static class ItemTypeStat {
        private int totalPlays;
    }
}
