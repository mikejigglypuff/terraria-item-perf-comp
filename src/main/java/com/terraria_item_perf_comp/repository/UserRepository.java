package com.terraria_item_perf_comp.repository;

import com.terraria_item_perf_comp.models.User;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByIpHash(String ipHash);
    User save(User user);
}

