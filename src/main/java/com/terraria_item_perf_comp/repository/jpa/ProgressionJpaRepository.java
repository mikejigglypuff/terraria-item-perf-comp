package com.terraria_item_perf_comp.repository.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.terraria_item_perf_comp.models.Progression;
import com.terraria_item_perf_comp.repository.ProgressionRepository;

@Repository
public interface ProgressionJpaRepository extends JpaRepository<Progression, Integer>, ProgressionRepository {

    @Override
    @Query("SELECT MAX(p.id) FROM Progression p")
    Optional<Integer> findMaxId();
}


