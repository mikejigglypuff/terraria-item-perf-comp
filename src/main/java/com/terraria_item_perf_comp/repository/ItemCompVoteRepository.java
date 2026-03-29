package com.terraria_item_perf_comp.repository;

import java.util.List;

import com.terraria_item_perf_comp.repository.projections.CompRecentVoteRow;

public interface ItemCompVoteRepository {
    int upsertVote(int situationId, int userId, int chosenItemId, int voteCount, short compCount, String chooseReason);
    
    /**
     * 특정 아이템 ID에 대해 chooseReason을 CreatedDate 역순으로 최대 5개 가져옵니다.
     * 
     * @param itemId 아이템 ID
     * @return chooseReason 리스트 (최대 5개, CreatedDate 역순)
     */
    List<String> findTop5ReasonsByItemIdOrderByCreatedAtDesc(int itemId);

    /**
     * 동일 title·category에 속한 comp 상황들에 대한 투표 중, excludeUserId가 아닌 사용자의 최근 기록을 최신순으로 반환합니다.
     *
     * @param excludeUserId 제외할 사용자(요청자); null이면 모든 사용자 포함
     * null값이 들어올 수 있도록 Wrapper 클래스 사용
     */
    List<CompRecentVoteRow> findRecentSelectionsByTitleAndCategoryExcludingUser(
            int titleId,
            int categoryId,
            Integer excludeUserId,
            int limit
    );
}


