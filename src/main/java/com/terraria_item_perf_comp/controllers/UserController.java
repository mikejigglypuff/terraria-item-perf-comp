package com.terraria_item_perf_comp.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import com.terraria_item_perf_comp.DTO.responses.UserInfoResDto;
import com.terraria_item_perf_comp.DTO.responses.UserStatResDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.terraria_item_perf_comp.DTO.requests.UserNickReqDto;
import com.terraria_item_perf_comp.DTO.requests.UserPwReqDto;
import com.terraria_item_perf_comp.DTO.responses.VO.CompStat;
import com.terraria_item_perf_comp.DTO.responses.VO.BalanceStat;
import com.terraria_item_perf_comp.DTO.responses.VO.RecentSituation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
public class UserController {
  @GetMapping("/")
  public ResponseEntity<UserInfoResDto> getUserInfo() {
    // TODO: Implement get user info logic
    return ResponseEntity.ok(new UserInfoResDto("success", "nickname", "email", "profileUrl"));
  }

  @PutMapping("/nickname")
  public ResponseEntity<String> updateUserNickname(@RequestBody UserNickReqDto userNickReqDto) {
    // TODO: Implement update user nickname logic
    return ResponseEntity.ok("success");
  }

  @PutMapping("/password")
  public ResponseEntity<String> updateUserPassword(@RequestBody UserPwReqDto userPwReqDto) {
    // TODO: Implement update user password logic
    return ResponseEntity.ok("success");
  }

  @GetMapping("/stats")
  public ResponseEntity<UserStatResDto> getUserStats() {
    // TODO: Implement get user stats logic
    return ResponseEntity.ok(new UserStatResDto(
      "success", 
      new CompStat(), 
      new BalanceStat(0, 0, 0, 0), 
      new RecentSituation("progression",
        "itemName1",
        1,
        "itemName2",
        2,
        "chosenItemName",
        1,
        "chosenReason"
      )
    ));
  }

  @DeleteMapping("/")
  public ResponseEntity<String> deleteUser(HttpServletRequest request) {
    String token = (String) request.getAttribute("token");
    
    if (Objects.isNull(token) || token.isEmpty()) {
      return ResponseEntity.badRequest().body("Authorization header is missing or invalid");
    }
    // TODO: Implement delete user logic
    return ResponseEntity.ok("success");
  }
}
