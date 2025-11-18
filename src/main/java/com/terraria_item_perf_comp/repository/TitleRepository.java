package com.terraria_item_perf_comp.repository;

import com.terraria_item_perf_comp.models.Title;

import java.util.List;

/**
 * Domain-level repository interface for Title entity.
 * This interface is framework-agnostic to reduce JPA/DB coupling.
 */
public interface TitleRepository {
    List<Title> findAll();
}


