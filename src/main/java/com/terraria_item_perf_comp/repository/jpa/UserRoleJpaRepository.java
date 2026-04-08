package com.terraria_item_perf_comp.repository.jpa;

import com.terraria_item_perf_comp.models.UserRole;
import com.terraria_item_perf_comp.repository.UserRoleRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleJpaRepository extends JpaRepository<UserRole, Integer>, UserRoleRepository {
}

