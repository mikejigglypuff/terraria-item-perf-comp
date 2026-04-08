package com.terraria_item_perf_comp.repository.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.terraria_item_perf_comp.models.ItemBalanceGame;

import jakarta.persistence.LockModeType;

@Repository
public interface ItemBalanceGameJpaRepository extends JpaRepository<ItemBalanceGame, Integer> {

    Optional<ItemBalanceGame> findFirstByUser_IdOrderByCreatedAtDesc(int userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select g from ItemBalanceGame g where g.id = :id")
    Optional<ItemBalanceGame> findByIdForUpdate(@Param("id") int id);
}
