package com.terraria_item_perf_comp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.terraria_item_perf_comp.models.Title;
import com.terraria_item_perf_comp.repository.TitleRepository;

@Service
public class TitleService {

    private final TitleRepository titleJpaRepository;

    public TitleService(TitleRepository titleJpaRepository) {
        this.titleJpaRepository = titleJpaRepository;
    }

    public List<Title> getAllTitles() {
        return titleJpaRepository.findAll();
    }
}


