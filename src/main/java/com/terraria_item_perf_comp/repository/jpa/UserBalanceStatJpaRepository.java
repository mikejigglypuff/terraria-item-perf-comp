package com.terraria_item_perf_comp.repository.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.terraria_item_perf_comp.models.UserBalanceStat;
import com.terraria_item_perf_comp.repository.UserBalanceStatRepository;

@Repository
public interface UserBalanceStatJpaRepository
        extends JpaRepository<UserBalanceStat, Integer>, UserBalanceStatRepository {

    @Override
    Optional<UserBalanceStat> findByUserIdAndTitleId(int userId, int titleId);
}


