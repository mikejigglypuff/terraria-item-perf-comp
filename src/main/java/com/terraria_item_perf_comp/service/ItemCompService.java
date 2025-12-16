package com.terraria_item_perf_comp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.terraria_item_perf_comp.DTO.responses.BalanceChooseResDto;
import com.terraria_item_perf_comp.DTO.responses.VO.ItemSelectionRate;
import com.terraria_item_perf_comp.repository.ItemBalanceVoteRepository;
import com.terraria_item_perf_comp.repository.ItemCompSituationRepository;
import com.terraria_item_perf_comp.repository.ItemCompVoteRepository;

@Service
public class ItemCompService {

    private static final int DEFAULT_USER_ID = 1;

    private final ItemCompSituationRepository itemCompSituationRepository;
    private final ItemCompVoteRepository itemCompVoteRepository;
    private final ItemBalanceVoteRepository itemBalanceVoteRepository;

    public ItemCompService(
            ItemCompSituationRepository itemCompSituationRepository,
            ItemCompVoteRepository itemCompVoteRepository,
            ItemBalanceVoteRepository itemBalanceVoteRepository
    ) {
        this.itemCompSituationRepository = itemCompSituationRepository;
        this.itemCompVoteRepository = itemCompVoteRepository;
        this.itemBalanceVoteRepository = itemBalanceVoteRepository;
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

    public List<ItemSelectionRate> recordBalanceVoteAndGetRates(
            int titleId,
            int categoryId,
            int progressionId,
            int chosenItemId,
            int notChosenId,
            int item1Id,
            int item2Id
    ) {
        int situationId = ensureSituationId(
                titleId,
                progressionId,
                categoryId,
                item1Id,
                item2Id,
                chosenItemId,
                notChosenId
        );

        itemBalanceVoteRepository.upsertBalanceVote(situationId, DEFAULT_USER_ID, chosenItemId);

        Double chosenRateValue = itemBalanceVoteRepository.findSelectionRate(
                situationId,
                DEFAULT_USER_ID,
                chosenItemId
        );
        double chosenRate = clampRate(chosenRateValue);
        double otherRate = clampRate(1.0 - chosenRate);

        List<ItemSelectionRate> rates = new ArrayList<>(2);
        rates.add(new ItemSelectionRate(chosenItemId, chosenRate));
        rates.add(new ItemSelectionRate(notChosenId, otherRate));
        return rates;
    }

    public BalanceChooseResDto processBalanceChoiceAndGetResponse(
            int titleId,
            int categoryId,
            int progressionId,
            int chosenItemId,
            int notChosenId,
            int item1Id,
            int item2Id
    ) {
        List<ItemSelectionRate> itemSelectionRates = recordBalanceVoteAndGetRates(
                titleId,
                categoryId,
                progressionId,
                chosenItemId,
                notChosenId,
                item1Id,
                item2Id
        );

        // itemSelectionRates에서 첫 번째가 item1, 두 번째가 item2
        int item1IdFromRates = itemSelectionRates.get(0).itemId();
        int item2IdFromRates = itemSelectionRates.get(1).itemId();

        List<String> item1Reasons = itemCompVoteRepository.findTop5ReasonsByItemIdOrderByCreatedAtDesc(item1IdFromRates);
        List<String> item2Reasons = itemCompVoteRepository.findTop5ReasonsByItemIdOrderByCreatedAtDesc(item2IdFromRates);

        return new BalanceChooseResDto(
                "success",
                itemSelectionRates,
                item1Reasons,
                item2Reasons
        );
    }

    private double clampRate(Double value) {
        if (value == null) {
            return 0.0;
        }
        if (value < 0.0) {
            return 0.0;
        }
        if (value > 1.0) {
            return 1.0;
        }
        return value;
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

