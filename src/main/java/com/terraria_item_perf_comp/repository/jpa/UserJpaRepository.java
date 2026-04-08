package com.terraria_item_perf_comp.repository.jpa;

import com.terraria_item_perf_comp.models.User;
import com.terraria_item_perf_comp.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Integer>, UserRepository {

    @Override
    @Query("SELECT u FROM User u WHERE u.ipHash = :ipHash AND u.deletedAt IS NULL")
    Optional<User> findByIpHash(@Param("ipHash") String ipHash);
}

