package com.terraria_item_perf_comp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.terraria_item_perf_comp.DTO.responses.CompRecentSelectionsResDto;
import com.terraria_item_perf_comp.DTO.responses.VO.CompOtherUserSelectionDto;
import com.terraria_item_perf_comp.repository.projections.CompRecentVoteRow;
import com.terraria_item_perf_comp.repository.ItemCompSituationRepository;
import com.terraria_item_perf_comp.repository.ItemCompVoteRepository;

@Service
public class ItemCompService {

    private static final int DEFAULT_USER_ID = 1;
    private static final int RECENT_SELECTIONS_MIN = 1;
    private static final int RECENT_SELECTIONS_MAX = 50;

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
        int situationId = ensureSituationId(
                titleId,
                progressionId,
                categoryId,
                item1Id,
                item2Id,
                chosenItemId,
                otherItemId
        );

        int userId = DEFAULT_USER_ID;
        int voteCount = 1;
        short compCount = 1;

        itemCompVoteRepository.upsertVote(
                situationId,
                userId,
                chosenItemId,
                voteCount,
                compCount,
                chosenReason
        );
    }

    /**
     * 동일 (titleId, categoryId)에 해당하는 comp 상황들에 대한 투표 중,
     * {@code excludeUserId}에 해당하지 않는 사용자들의 최근 선택 기록을 최신순으로 반환합니다.
     *
     * @param excludeUserId 본인 제외용 사용자 ID; null이면 모든 사용자의 기록을 포함합니다.
     * @param limit           반환할 최대 건수 (1~50, 범위 밖이면 보정)
     */
    public CompRecentSelectionsResDto getRecentOtherUserSelections(
            int titleId,
            int categoryId,
            int limit,
            Integer excludeUserId
    ) {
        int capped = clampRecentSelectionLimit(limit);
        List<CompRecentVoteRow> rows = itemCompVoteRepository.findRecentSelectionsByTitleAndCategoryExcludingUser(
                titleId,
                categoryId,
                excludeUserId,
                capped
        );
        List<CompOtherUserSelectionDto> selections = rows.stream()
                .map(r -> new CompOtherUserSelectionDto(
                        r.getChosenItemId(),
                        r.getChooseReason(),
                        r.getCreatedAt(),
                        r.getProgressionId(),
                        r.getItem1Id(),
                        r.getItem2Id()
                ))
                .toList();
        return new CompRecentSelectionsResDto("success", selections);
    }

    private static int clampRecentSelectionLimit(int limit) {
        if (limit < RECENT_SELECTIONS_MIN) {
            return RECENT_SELECTIONS_MIN;
        }
        if (limit > RECENT_SELECTIONS_MAX) {
            return RECENT_SELECTIONS_MAX;
        }
        return limit;
    }

    /**
     * comp/밸런스 공통으로 {@code item_comp_situations} 행을 보장하고 해당 id를 반환합니다.
     */
    public int ensureCompSituationForPair(
            int titleId,
            int progressionId,
            int categoryId,
            int item1Id,
            int item2Id,
            int chosenItemId,
            int notChosenId
    ) {
        return ensureSituationId(
                titleId,
                progressionId,
                categoryId,
                item1Id,
                item2Id,
                chosenItemId,
                notChosenId
        );
    }

    private int ensureSituationId(
            int titleId,
            int progressionId,
            int categoryId,
            int item1Id,
            int item2Id,
            int chosenItemId,
            int notChosenId
    ) {
        Optional<Integer> situationIdOpt = itemCompSituationRepository.findSituationId(
                titleId,
                progressionId,
                categoryId,
                item1Id,
                item2Id
        );
        if (situationIdOpt.isPresent()) {
            return situationIdOpt.get();
        }

        itemCompSituationRepository.insertByIds(
                titleId,
                progressionId,
                categoryId,
                chosenItemId,
                notChosenId
        );

        return itemCompSituationRepository.findSituationId(
                titleId,
                progressionId,
                categoryId,
                item1Id,
                item2Id
        ).orElseThrow(() ->
                new IllegalStateException("Failed to create item_comp_situations entry for items %d / %d"
                        .formatted(item1Id, item2Id))
        );
    }
}

