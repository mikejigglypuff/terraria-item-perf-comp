package com.terraria_item_perf_comp.DTO.requests;

public record OAuthRegisterReqDto(
        boolean acceptName,
        boolean acceptEmail,
        String state,
        String authorizationCode
) {
}
