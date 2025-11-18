package com.terraria_item_perf_comp.repository;

import java.util.Optional;

import com.terraria_item_perf_comp.models.UserBalanceStat;

public interface UserBalanceStatRepository {
    Optional<UserBalanceStat> findByUserIdAndTitleId(int userId, int titleId);
    UserBalanceStat save(UserBalanceStat stat);
}


