package com.terraria_item_perf_comp.controllers;

import java.util.*;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;

import com.terraria_item_perf_comp.DTO.responses.GameTitleResDto;
import com.terraria_item_perf_comp.models.Title;

import lombok.RequiredArgsConstructor;

import com.terraria_item_perf_comp.DTO.responses.ItemCategoryResDto;
import com.terraria_item_perf_comp.DTO.responses.GameStartResDto;
import com.terraria_item_perf_comp.models.Progression;
import com.terraria_item_perf_comp.models.Item;
import com.terraria_item_perf_comp.DTO.requests.GameStartReqDto;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.terraria_item_perf_comp.models.ItemCategory;
import com.terraria_item_perf_comp.DTO.requests.CompChooseReqDto;
import com.terraria_item_perf_comp.DTO.requests.BalanceChooseReqDto;
import com.terraria_item_perf_comp.DTO.responses.BalanceChooseResDto;

import org.springframework.web.bind.annotation.GetMapping;

import com.terraria_item_perf_comp.service.ItemStatService;
import com.terraria_item_perf_comp.service.ItemService;
import com.terraria_item_perf_comp.service.ItemCategoryService;
import com.terraria_item_perf_comp.service.ItemCompService;
import com.terraria_item_perf_comp.service.TitleService;
import com.terraira_item_perf_comp.service.ProgressionService;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {
  private final TitleService titleService;
  private final ItemCategoryService itemCategoryService;
  private final ItemService itemService;
  private final ItemCompService itemCompService;
  private final ItemStatService itemStatService;
  private final com.terraria_item_perf_comp.service.ProgressionService progressionService;

  @GetMapping("/titles")
  public ResponseEntity<GameTitleResDto> getGameTitles() {
    return ResponseEntity.ok(new GameTitleResDto("success", titleService.getAllTitles()));
  }

  @GetMapping("/titles/{titleId}/categories")
  public ResponseEntity<ItemCategoryResDto> getGameCategories(@PathVariable int titleId) {
    return ResponseEntity.ok(new ItemCategoryResDto("success", itemCategoryService.getItemCategoriesByTitleId(titleId)));
  }

  @GetMapping("/start")
  public ResponseEntity<GameStartResDto> startGame(@RequestBody GameStartReqDto gameStartReqDto) {
    int progressionId = progressionService.getRandomProgressionId();
    return ResponseEntity.ok(new GameStartResDto(
      "success", new Progression(), itemService.getUnseenItemPairs(gameStartReqDto.titleId(), gameStartReqDto.categoryId(), progressionId)));
  }

  @PostMapping("/comp/choose")
  public ResponseEntity<String> compChoose(@RequestBody CompChooseReqDto gameCompChooseReqDto) {
    itemCompService.processCompChoice(
      gameCompChooseReqDto.categoryId(), gameCompChooseReqDto.chosenId(), gameCompChooseReqDto.titleId(), gameCompChooseReqDto.progressionId(), 
      gameCompChooseReqDto.notChosenId(), gameCompChooseReqDto.item1Id(), gameCompChooseReqDto.item2Id(), gameCompChooseReqDto.chooseReason());
    return ResponseEntity.ok("success");
  }

  @PostMapping("/balance/choose")
  public ResponseEntity<BalanceChooseResDto> balanceChoose(@RequestBody BalanceChooseReqDto gameBalanceChooseReqDto) {
    BalanceChooseResDto response = itemCompService.processBalanceChoiceAndGetResponse(
      gameBalanceChooseReqDto.titleId(),
      gameBalanceChooseReqDto.categoryId(),
      gameBalanceChooseReqDto.progressionId(),
      gameBalanceChooseReqDto.chosenId(),
      gameBalanceChooseReqDto.notChosenId(),
      gameBalanceChooseReqDto.chosenId(),
      gameBalanceChooseReqDto.notChosenId()
    );
    return ResponseEntity.ok(response);
  }
}
