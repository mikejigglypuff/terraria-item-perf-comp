package com.terraria_item_perf_comp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.terraria_item_perf_comp.models.Progression;

@NoRepositoryBean
public interface ProgressionRepository extends JpaRepository<Progression, Integer> {
    Optional<Integer> findMaxId();
}


