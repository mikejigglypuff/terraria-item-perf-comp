package com.terraria_item_perf_comp.repository.jpa;

import com.terraria_item_perf_comp.models.ItemCategory;
import com.terraria_item_perf_comp.repository.ItemCategoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemCategoryJpaRepository extends JpaRepository<ItemCategory, Integer>, ItemCategoryRepository {
    @Override
    List<ItemCategory> findByTitleId(int titleId);
}


