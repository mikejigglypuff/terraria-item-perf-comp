package com.terraria_item_perf_comp.DTO.requests;

public record RegisterReqDto(
        String email,
        String nickname,
        String pw
) {
}
