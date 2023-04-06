package com.danram.server.controller;

import com.danram.server.domain.Member;
import com.danram.server.dto.response.LoginResponseDto;
import com.danram.server.oauth.SocialLoginType;
import com.danram.server.service.oauth.GoogleLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/login")
@Slf4j
public class LoginController {
    private final GoogleLoginService googleLoginService;

    @GetMapping(value = "/{socialLoginType}/token")
    public ResponseEntity<LoginResponseDto> requestToken(
            @PathVariable(name = "socialLoginType") SocialLoginType socialLoginType,
            @RequestParam(name = "code") String code) {
        final Member member = googleLoginService.signUp();

        return ResponseEntity.ok(googleLoginService.generateTokens(member));

    }
}
