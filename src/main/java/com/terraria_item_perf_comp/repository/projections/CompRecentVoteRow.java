package com.terraria_item_perf_comp.repository.projections;

import java.time.LocalDateTime;

/**
 * Native query projection for recent comp votes joined with situations.
 */
public interface CompRecentVoteRow {

    int getChosenItemId();

    String getChooseReason();

    LocalDateTime getCreatedAt();

    int getProgressionId();

    int getItem1Id();

    int getItem2Id();
}
