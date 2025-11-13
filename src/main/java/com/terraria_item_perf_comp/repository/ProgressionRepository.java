package com.terraria_item_perf_comp.repository;

import java.util.Optional;

public interface ProgressionRepository {
    Optional<Integer> findMaxId();
}


