package com.terraria_item_perf_comp.repository.jpa;

import com.terraria_item_perf_comp.models.ItemCategory;
import com.terraria_item_perf_comp.repository.ItemCategoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemCategoryJpaRepository extends JpaRepository<ItemCategory, Integer>, ItemCategoryRepository {
    @Override
    List<ItemCategory> findByTitleId(int titleId);
}


