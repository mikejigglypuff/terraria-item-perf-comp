package com.terraria_item_perf_comp.repository;

public interface ItemBalanceVoteRepository {
    /**
     * item_balance_votes에 투표를 추가하거나 기존 투표의 choose_count를 증가시킵니다.
     * 
     * @param situationId item_comp_situations의 ID
     * @param userId 사용자 ID
     * @param chosenItemId 선택된 아이템 ID
     * @return 영향받은 행 수
     */
    int upsertBalanceVote(int situationId, int userId, int chosenItemId);
}

