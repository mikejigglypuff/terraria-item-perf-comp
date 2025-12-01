package com.terraria_item_perf_comp.repository;

import java.util.List;

public interface ItemCompVoteRepository {
    int upsertVote(int situationId, int userId, int chosenItemId, int voteCount, short compCount, String chooseReason);
    
    /**
     * 특정 아이템 ID에 대해 chooseReason을 CreatedDate 역순으로 최대 5개 가져옵니다.
     * 
     * @param itemId 아이템 ID
     * @return chooseReason 리스트 (최대 5개, CreatedDate 역순)
     */
    List<String> findTop5ReasonsByItemIdOrderByCreatedAtDesc(int itemId);
}


