package com.terraria_item_perf_comp.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import com.terraria_item_perf_comp.DTO.responses.GameTitleResDto;
import com.terraria_item_perf_comp.models.Title;
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

@RestController
@RequestMapping("/api/game")
public class GameController {
  @GetMapping("/titles")
  public ResponseEntity<GameTitleResDto> getGameTitles() {
    // TODO: Implement get game titles logic
    return ResponseEntity.ok(new GameTitleResDto("success", new Title[0]));
  }

  @GetMapping("/titles/${titleId}/categories")
  public ResponseEntity<ItemCategoryResDto> getGameCategories(@PathVariable int titleId) {
    // TODO: Implement get game categories logic
    return ResponseEntity.ok(new ItemCategoryResDto("success", new ItemCategory[0]));
  }

  @GetMapping("/start")
  public ResponseEntity<GameStartResDto> startGame(@RequestBody GameStartReqDto gameStartReqDto) {
    // TODO: Implement start game logic
    return ResponseEntity.ok(new GameStartResDto(
      "success", new Progression(), new Item(), new Item()));
  }

  @PostMapping("/comp/choose")
  public ResponseEntity<String> compChoose(@RequestBody CompChooseReqDto gameCompChooseReqDto) {
    // TODO: Implement comp choose logic
    return ResponseEntity.ok("success");
  }

  @PostMapping("/balance/choose")
  public ResponseEntity<BalanceChooseResDto> balanceChoose(@RequestBody BalanceChooseReqDto gameBalanceChooseReqDto) {
    // TODO: Implement balance choose logic
    return ResponseEntity.ok(new BalanceChooseResDto("success", 0, 0, new String[0], new String[0]));
  }
}
