package com.terraria_item_perf_comp.repository;

import com.terraria_item_perf_comp.models.UserRole;
import java.util.Optional;

public interface UserRoleRepository {
    Optional<UserRole> findById(int id);
}

