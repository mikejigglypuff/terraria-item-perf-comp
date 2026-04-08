package com.terraria_item_perf_comp.DTO.requests;

public record GameStartReqDto(
        int titleId,
        int categoryId,
        int chooseNum // 추후에 클라이언트에서 선택 횟수 사용할 수 있음
) {
}
