package com.terraria_item_perf_comp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.terraria_item_perf_comp.DTO.requests.BalanceChooseReqDto;
import com.terraria_item_perf_comp.DTO.requests.CompChooseReqDto;
import com.terraria_item_perf_comp.DTO.responses.BalanceChooseResDto;
import com.terraria_item_perf_comp.DTO.responses.CompRecentSelectionsResDto;
import com.terraria_item_perf_comp.DTO.responses.GameStartResDto;
import com.terraria_item_perf_comp.DTO.responses.GameTitleResDto;
import com.terraria_item_perf_comp.DTO.responses.ItemCategoryResDto;
import com.terraria_item_perf_comp.DTO.responses.VO.ProgressionDto;
import com.terraria_item_perf_comp.DTO.responses.VO.TitleDto;
import com.terraria_item_perf_comp.models.Progression;
import com.terraria_item_perf_comp.models.Title;
import com.terraria_item_perf_comp.service.ItemCategoryService;
import com.terraria_item_perf_comp.service.ItemBalanceGameService;
import com.terraria_item_perf_comp.service.ItemCompService;
import com.terraria_item_perf_comp.service.ItemService;
import com.terraria_item_perf_comp.service.ItemStatService;
import com.terraria_item_perf_comp.service.ProgressionService;
import com.terraria_item_perf_comp.service.TitleService;
import com.terraria_item_perf_comp.web.GuestUserIdResolver;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {
  private final TitleService titleService;
  private final ItemCategoryService itemCategoryService;
  private final ItemService itemService;
  private final ItemCompService itemCompService;
  private final ItemBalanceGameService itemBalanceGameService;
  private final GuestUserIdResolver guestUserIdResolver;
  private final ItemStatService itemStatService;
  private final ProgressionService progressionService;

  @GetMapping("/titles")
  public ResponseEntity<GameTitleResDto> getGameTitles() {
    return ResponseEntity.ok(new GameTitleResDto("success", titleService.getAllTitles()));
  }

  @GetMapping("/titles/{titleId}/categories")
  public ResponseEntity<ItemCategoryResDto> getGameCategories(@PathVariable int titleId) {
    return ResponseEntity.ok(new ItemCategoryResDto("success", itemCategoryService.getItemCategoriesByTitleId(titleId)));
  }

  @GetMapping("/start")
  public ResponseEntity<GameStartResDto> startGame(
      @RequestParam int titleId,
      @RequestParam int categoryId,
      @RequestParam int chooseNum,
      @RequestParam(defaultValue = "false") boolean balance,
      HttpServletRequest request
  ) {
    Progression selectedProgression = progressionService.getRandomIdProgression();
    Title title = selectedProgression.getTitle();
    TitleDto titleDto = new TitleDto(title.getId(), title.getTitle(), title.getImgUrl());
    ProgressionDto progressionDto = new ProgressionDto(
        selectedProgression.getId(),
        selectedProgression.getProgressName(),
        titleDto,
        selectedProgression.getImgUrl()
    );
    Integer balanceGameId = null;
    if (balance) {
      int userId = guestUserIdResolver.resolveRequiredUserId(request);
      balanceGameId = itemBalanceGameService.startBalanceGame(titleId, userId, chooseNum);
    }
    return ResponseEntity.ok(new GameStartResDto(
        "success",
        progressionDto,
        itemService.getUnseenItemPairs(titleId, categoryId, selectedProgression.getId()),
        balanceGameId
    ));
  }

  @PostMapping("/comp/choose")
  public ResponseEntity<String> compChoose(@RequestBody CompChooseReqDto gameCompChooseReqDto) {
    itemCompService.processCompChoice(
      gameCompChooseReqDto.categoryId(), gameCompChooseReqDto.chosenId(), gameCompChooseReqDto.titleId(), gameCompChooseReqDto.progressionId(), 
      gameCompChooseReqDto.notChosenId(), gameCompChooseReqDto.item1Id(), gameCompChooseReqDto.item2Id(), gameCompChooseReqDto.chooseReason());
    return ResponseEntity.ok("success");
  }

  /**
   * 동일 (titleId, categoryId)에 대한 comp 투표 중, 다른 사용자들의 최근 선택 기록을 조회합니다.
   *
   * @param excludeUserId 요청 사용자 본인을 제외할 때 사용자 ID(게스트 등). 생략 시 모든 사용자 기록을 포함합니다.
   * @param limit           최대 N건 (1~50, 기본 10)
   */
  @GetMapping("/comp/recent-selections")
  public ResponseEntity<CompRecentSelectionsResDto> getRecentCompSelections(
      @RequestParam int titleId,
      @RequestParam int categoryId,
      @RequestParam(defaultValue = "10") int limit,
      @RequestParam(required = false) Integer excludeUserId
  ) {
    return ResponseEntity.ok(
        itemCompService.getRecentOtherUserSelections(titleId, categoryId, limit, excludeUserId)
    );
  }

  @PostMapping("/balance/choose")
  public ResponseEntity<BalanceChooseResDto> balanceChoose(
      @RequestBody BalanceChooseReqDto gameBalanceChooseReqDto,
      HttpServletRequest request
  ) {
    int userId = guestUserIdResolver.resolveRequiredUserId(request);
    BalanceChooseResDto response = itemBalanceGameService.processBalanceChoiceAndGetResponse(
        gameBalanceChooseReqDto.gameId(),
        userId,
        gameBalanceChooseReqDto.titleId(),
        gameBalanceChooseReqDto.categoryId(),
        gameBalanceChooseReqDto.progressionId(),
        gameBalanceChooseReqDto.chosenId(),
        gameBalanceChooseReqDto.notChosenId(),
        gameBalanceChooseReqDto.item1Id(),
        gameBalanceChooseReqDto.item2Id()
    );
    return ResponseEntity.ok(response);
  }
}
