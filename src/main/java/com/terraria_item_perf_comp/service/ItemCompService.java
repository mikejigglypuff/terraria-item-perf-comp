package com.terraria_item_perf_comp.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.terraria_item_perf_comp.repository.ItemCompSituationRepository;
import com.terraria_item_perf_comp.repository.ItemCompVoteRepository;

@Service
public class ItemCompService {

    private final ItemCompSituationRepository itemCompSituationRepository;
    private final ItemCompVoteRepository itemCompVoteRepository;

    public ItemCompService(
            ItemCompSituationRepository itemCompSituationRepository,
            ItemCompVoteRepository itemCompVoteRepository
    ) {
        this.itemCompSituationRepository = itemCompSituationRepository;
        this.itemCompVoteRepository = itemCompVoteRepository;
    }

    public void processCompChoice(
            int categoryId,
            int chosenItemId,
            int titleId,
            int progressionId,
            int otherItemId,
            int item1Id,
            int item2Id,
            String chosenReason
    ) {
        // compItem1, compItem2는 item1Id, item2Id로 결정
        int compItem1 = item1Id;
        int compItem2 = item2Id;

        // 조합 존재 여부 확인 (comp_item_min, comp_item_max 기준)
        Optional<Integer> situationIdOpt = itemCompSituationRepository.findSituationId(
                titleId, progressionId, categoryId, compItem1, compItem2
        );

        if (situationIdOpt.isEmpty()) {
            // 조합이 없으면 insertByIds 수행
            // chosenId와 notChosenId는 chosenItemId와 otherItemId 사용
            int chosenId = chosenItemId;
            int notChosenId = otherItemId;
            
            itemCompSituationRepository.insertByIds(
                    titleId, progressionId, categoryId, chosenId, notChosenId
            );
        } else {
            // 조합이 있으면 upsertVote 수행
            int situationId = situationIdOpt.get();
            // userId와 compCount는 파라미터에 없으므로 기본값 사용
            // TODO: userId는 실제 사용자 ID를 받아야 할 수 있음
            int userId = 0; // 기본값, 필요시 파라미터로 받도록 수정 필요
            int voteCount = 1; // 기본값
            short compCount = 1; // 기본값

            itemCompVoteRepository.upsertVote(
                    situationId,
                    userId,
                    chosenItemId,
                    voteCount,
                    compCount,
                    chosenReason
            );
        }
    }
}

