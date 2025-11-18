package com.terraria_item_perf_comp.repository.jpa;

import com.terraria_item_perf_comp.models.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.terraria_item_perf_comp.repository.TitleRepository;

@Repository
public interface TitleJpaRepository extends JpaRepository<Title, Integer>, TitleRepository {
}


