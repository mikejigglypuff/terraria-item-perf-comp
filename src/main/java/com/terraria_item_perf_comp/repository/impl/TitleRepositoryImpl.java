package com.terraria_item_perf_comp.repository.impl;

import com.terraria_item_perf_comp.models.Title;
import com.terraria_item_perf_comp.repository.TitleRepository;
import com.terraria_item_perf_comp.repository.jpa.TitleJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TitleRepositoryImpl implements TitleRepository {

    private final TitleJpaRepository titleJpaRepository;

    public TitleRepositoryImpl(TitleJpaRepository titleJpaRepository) {
        this.titleJpaRepository = titleJpaRepository;
    }

    @Override
    public List<Title> findAll() {
        return titleJpaRepository.findAll();
    }
}


