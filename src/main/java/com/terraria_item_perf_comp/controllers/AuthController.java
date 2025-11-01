package com.terraria_item_perf_comp.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import com.terraria_item_perf_comp.DTO.requests.RegisterReqDto;
import com.terraria_item_perf_comp.DTO.requests.RegisterVerifyReqDto;
import com.terraria_item_perf_comp.DTO.requests.LoginReqDto;
import com.terraria_item_perf_comp.DTO.requests.OAuthLoginReqDto;
import com.terraria_item_perf_comp.DTO.requests.OAuthRegisterReqDto;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {
  @PostMapping("/register/verify")
  public ResponseEntity<String> verifyRegister(@RequestBody RegisterVerifyReqDto registerVerifyReqDto) {
    // TODO: Implement register verify logic
    return ResponseEntity.ok("Register verify successful");
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody RegisterReqDto registerReqDto) {
    // TODO: Implement register logic
    return ResponseEntity.ok("Register successful");
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginReqDto loginReqDto) {
    // TODO: Implement login logic
    return ResponseEntity.ok("Login successful");
  }

  @PostMapping("/register/oauth")
  public ResponseEntity<String> registerOauth(@RequestBody OAuthRegisterReqDto oauthRegisterReqDto) {
    // TODO: Implement register oauth logic
    return ResponseEntity.ok("Register oauth successful");
  }

  @PostMapping("/login/oauth")
  public ResponseEntity<String> loginOauth(@RequestBody OAuthLoginReqDto oauthLoginReqDto) {
    // TODO: Implement login oauth logic
    return ResponseEntity.ok("Login oauth successful");
  }

  @PostMapping("/refresh")
  public ResponseEntity<String> refresh(HttpServletRequest request) {
    String token = (String) request.getAttribute("token");
    
    if (Objects.isNull(token) || token.isEmpty()) {
      return ResponseEntity.badRequest().body("Authorization header is missing or invalid");
    }

    // TODO: Implement token refresh logic
    return ResponseEntity.ok("Refresh successful");
  }
}
