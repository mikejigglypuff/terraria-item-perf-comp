package com.terraria_item_perf_comp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.terraria_item_perf_comp.DTO.responses.BalanceChooseResDto;
import com.terraria_item_perf_comp.DTO.responses.VO.ItemSelectionRate;
import com.terraria_item_perf_comp.exception.BalanceGameException;
import com.terraria_item_perf_comp.models.ItemBalanceGame;
import com.terraria_item_perf_comp.models.ItemBalanceGameStatus;
import com.terraria_item_perf_comp.models.Title;
import com.terraria_item_perf_comp.models.User;
import com.terraria_item_perf_comp.repository.ItemBalanceVoteRepository;
import com.terraria_item_perf_comp.repository.ItemCompVoteRepository;
import com.terraria_item_perf_comp.repository.jpa.ItemBalanceGameJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemBalanceGameService {

    private final ItemBalanceGameJpaRepository itemBalanceGameRepository;
    private final ItemBalanceVoteRepository itemBalanceVoteRepository;
    private final ItemCompVoteRepository itemCompVoteRepository;
    private final ItemCompService itemCompService;

    @Transactional
    public int startBalanceGame(int titleId, int userId, int expectedRounds) {
        if (expectedRounds < 1) {
            throw new BalanceGameException(HttpStatus.BAD_REQUEST, "expectedRounds must be at least 1");
        }
        itemBalanceGameRepository.findFirstByUser_IdOrderByCreatedAtDesc(userId).ifPresent(latest -> {
            if (latest.getStatus() != ItemBalanceGameStatus.COMPLETED) {
                latest.abandon();
                itemBalanceGameRepository.save(latest);
            }
        });
        ItemBalanceGame game = ItemBalanceGame.builder()
                .title(Title.builder().id(titleId).build())
                .user(User.builder().id(userId).build())
                .status(ItemBalanceGameStatus.READY)
                .expectedRounds(expectedRounds)
                .votedRounds(0)
                .build();
        return itemBalanceGameRepository.save(game).getId();
    }

    @Transactional
    public BalanceChooseResDto processBalanceChoiceAndGetResponse(
            int gameId,
            int userId,
            int titleId,
            int categoryId,
            int progressionId,
            int chosenItemId,
            int notChosenId,
            int item1Id,
            int item2Id
    ) {
        ItemBalanceGame game = itemBalanceGameRepository.findByIdForUpdate(gameId)
                .orElseThrow(() -> new BalanceGameException(HttpStatus.NOT_FOUND, "Balance game not found"));

        if (game.getUser().getId() != userId) {
            throw new BalanceGameException(HttpStatus.FORBIDDEN, "Balance game does not belong to the current user");
        }
        if (game.getTitle().getId() != titleId) {
            throw new BalanceGameException(HttpStatus.BAD_REQUEST, "titleId does not match this balance game");
        }

        ItemBalanceGameStatus status = game.getStatus();
        if (status != ItemBalanceGameStatus.READY && status != ItemBalanceGameStatus.ACTIVE) {
            throw new BalanceGameException(HttpStatus.CONFLICT, "Balance game is not accepting votes");
        }

        game.activateIfReady();

        int situationId = itemCompService.ensureCompSituationForPair(
                titleId,
                progressionId,
                categoryId,
                item1Id,
                item2Id,
                chosenItemId,
                notChosenId
        );

        itemBalanceVoteRepository.upsertBalanceVote(situationId, gameId, userId, chosenItemId);
        game.registerVoteRound();
        if (game.getVotedRounds() > game.getExpectedRounds()) {
            throw new BalanceGameException(HttpStatus.BAD_REQUEST, "Too many votes for this balance game");
        }
        game.completeIfFinished();
        itemBalanceGameRepository.save(game);

        Double chosenRateValue = itemBalanceVoteRepository.findSelectionRate(
                situationId,
                gameId,
                userId,
                chosenItemId
        );
        double chosenRate = clampRate(chosenRateValue);
        double otherRate = clampRate(1.0 - chosenRate);

        double rateForItem1 = item1Id == chosenItemId ? chosenRate : otherRate;
        double rateForItem2 = item2Id == chosenItemId ? chosenRate : otherRate;

        List<ItemSelectionRate> itemSelectionRates = new ArrayList<>(2);
        itemSelectionRates.add(new ItemSelectionRate(item1Id, rateForItem1));
        itemSelectionRates.add(new ItemSelectionRate(item2Id, rateForItem2));

        List<String> item1Reasons = itemCompVoteRepository.findTop5ReasonsByItemIdOrderByCreatedAtDesc(item1Id);
        List<String> item2Reasons = itemCompVoteRepository.findTop5ReasonsByItemIdOrderByCreatedAtDesc(item2Id);

        return new BalanceChooseResDto(
                "success",
                itemSelectionRates,
                item1Reasons,
                item2Reasons
        );
    }

    private static double clampRate(Double value) {
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
}
