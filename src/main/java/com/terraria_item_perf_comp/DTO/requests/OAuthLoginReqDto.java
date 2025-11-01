package com.terraria_item_perf_comp.DTO.requests;

public record OAuthLoginReqDto(
        String state,
        String authorizationCode
) {
}
