package com.terraria_item_perf_comp.repository.jpa;

import com.terraria_item_perf_comp.models.Item;
import com.terraria_item_perf_comp.repository.projections.ItemPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.terraria_item_perf_comp.repository.ItemRepository;
import java.util.List;

@Repository
public interface ItemJpaRepository extends JpaRepository<Item, Integer>, ItemRepository {

    @Override
    @Query(value = "WITH candidate_pairs AS (\n" +
            "  SELECT\n" +
            "      i1.id AS item1_id,\n" +
            "      i2.id AS item2_id,\n" +
            "      LEAST(i1.id, i2.id) AS pair_min,\n" +
            "      GREATEST(i1.id, i2.id) AS pair_max\n" +
            "  FROM items i1\n" +
            "  JOIN items i2\n" +
            "    ON i1.id < i2.id\n" +
            "   AND i1.title_id    = :titleId\n" +
            "   AND i2.title_id    = :titleId\n" +
            "   AND i1.category_id = :categoryId\n" +
            "   AND i2.category_id = :categoryId\n" +
            "   AND i1.min_progression_id <= :progressionId\n" +
            "   AND i2.min_progression_id <= :progressionId\n" +
            ")\n" +
            "SELECT\n" +
            "  c.item1_id AS item1Id, c.item2_id AS item2Id\n" +
            "FROM candidate_pairs c\n" +
            "LEFT JOIN item_comp_situations s\n" +
            "  ON s.title_id       = :titleId\n" +
            " AND s.category_id    = :categoryId\n" +
            " AND s.progression_id = :progressionId\n" +
            " AND s.comp_item_min  = c.pair_min\n" +
            " AND s.comp_item_max  = c.pair_max\n" +
            "WHERE s.id IS NULL\n" +
            "ORDER BY RAND()\n" +
            "LIMIT :length",
            nativeQuery = true)
    List<ItemPair> findUnseenItemPairs(
            @Param("titleId") int titleId,
            @Param("categoryId") int categoryId,
            @Param("progressionId") int progressionId,
            @Param("length") int length
    );
}


