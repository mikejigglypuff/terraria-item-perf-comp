package com.terraria_item_perf_comp.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.terraria_item_perf_comp.exception.GameException;
import com.terraria_item_perf_comp.models.ItemCompGame;
import com.terraria_item_perf_comp.models.ItemCompGameStatus;
import com.terraria_item_perf_comp.models.ItemCategory;
import com.terraria_item_perf_comp.models.Title;
import com.terraria_item_perf_comp.models.User;
import com.terraria_item_perf_comp.repository.ItemCompVoteRepository;
import com.terraria_item_perf_comp.repository.jpa.ItemCompGameJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemCompGameService {

    private final ItemCompGameJpaRepository itemCompGameRepository;
    private final ItemCompVoteRepository itemCompVoteRepository;
    private final ItemCompService itemCompService;

    @Transactional
    public int startCompGame(int titleId, int userId, int expectedRounds, int categoryId) {
        if (expectedRounds < 1) {
            throw new GameException(HttpStatus.BAD_REQUEST, "expectedRounds must be at least 1");
        }

        itemCompGameRepository
                .findFirstByUser_IdAndTitle_IdAndExpectedRoundsAndCategory_IdAndStatusOrderByCreatedAtDesc(
                        userId,
                        titleId,
                        expectedRounds,
                        categoryId,
                        ItemCompGameStatus.ACTIVE
                )
                .ifPresent(active -> {
                    active.abandon();
                    itemCompGameRepository.save(active);
                });

        ItemCompGame game = ItemCompGame.builder()
                .title(Title.builder().id(titleId).build())
                .user(User.builder().id(userId).build())
                .category(ItemCategory.builder().id(categoryId).build())
                .status(ItemCompGameStatus.READY)
                .expectedRounds(expectedRounds)
                .votedRounds(0)
                .build();

        return itemCompGameRepository.save(game).getId();
    }

    @Transactional
    public void recordCompChoice(
            int userId,
            int titleId,
            int categoryId,
            int progressionId,
            int chosenItemId,
            int notChosenId,
            int item1Id,
            int item2Id,
            String chooseReason
    ) {
        ItemCompGame game = itemCompGameRepository
                .findFirstByUser_IdAndTitle_IdAndCategory_IdAndStatusInOrderByCreatedAtDesc(
                        userId,
                        titleId,
                        categoryId,
                        List.of(ItemCompGameStatus.READY, ItemCompGameStatus.ACTIVE)
                )
                .orElseThrow(() -> new GameException(HttpStatus.BAD_REQUEST, "No active/ready comp game found"));

        if (game.getVotedRounds() == 0) {
            game.activateIfReady();
        }
        if (game.getStatus() != ItemCompGameStatus.ACTIVE) {
            throw new GameException(HttpStatus.CONFLICT, "Comp game is not accepting votes");
        }

        int situationId = itemCompService.ensureCompSituationForPair(
                titleId,
                progressionId,
                categoryId,
                item1Id,
                item2Id,
                chosenItemId,
                notChosenId
        );

        itemCompVoteRepository.upsertVote(
                situationId,
                userId,
                chosenItemId,
                1,
                (short) 1,
                chooseReason
        );

        game.registerVoteRound();
        if (game.getVotedRounds() > game.getExpectedRounds()) {
            throw new GameException(HttpStatus.BAD_REQUEST, "Too many votes for this comp game");
        }
        game.completeIfFinished();
        itemCompGameRepository.save(game);
    }
}

