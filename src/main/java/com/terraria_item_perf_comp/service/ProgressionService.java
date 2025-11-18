package com.terraria_item_perf_comp.service;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.terraria_item_perf_comp.repository.ProgressionRepository;

@Service
public class ProgressionService {

    private final ProgressionRepository progressionRepository;

    public ProgressionService(ProgressionRepository progressionRepository) {
        this.progressionRepository = progressionRepository;
    }

    public int getRandomProgressionId() {
        Optional<Integer> maxIdOpt = progressionRepository.findMaxId();
        if (maxIdOpt.isEmpty()) {
            return -1;
        }

        int maxId = maxIdOpt.get();
        if (maxId < 1) {
            return -1;
        }

        // 스레드 별 난수 생성
        return ThreadLocalRandom.current().nextInt(1, maxId + 1);
    }
}


