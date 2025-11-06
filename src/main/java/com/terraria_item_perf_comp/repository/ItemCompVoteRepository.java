package com.terraria_item_perf_comp.repository;

public interface ItemCompVoteRepository {
    int upsertVote(int situationId, int userId, int chosenItemId, int voteCount, short compCount, String chooseReason);
}


