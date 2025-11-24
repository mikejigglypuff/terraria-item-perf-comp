package com.terraria_item_perf_comp.service;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.terraria_item_perf_comp.repository.ProgressionRepository;
import com.terraria_item_perf_comp.models.Progression;

@Service
public class ProgressionService {

    private final ProgressionRepository progressionRepository;

    public ProgressionService(ProgressionRepository progressionRepository) {
        this.progressionRepository = progressionRepository;
    }

    public Progression getRandomIdProgression() {
        Optional<Integer> maxIdOpt = progressionRepository.findMaxId();
        if (maxIdOpt.isEmpty()) {
            return Progression.builder().id(-1).build();
        }

        int maxId = maxIdOpt.get();
        if (maxId < 1) {
            return Progression.builder().id(-1).build();
        }

        // 스레드 별 난수 생성
        return progressionRepository.findById(ThreadLocalRandom.current().nextInt(1, maxId + 1))
          .orElseThrow(() -> new IllegalArgumentException("progression doesn't exist"));
    }
}


