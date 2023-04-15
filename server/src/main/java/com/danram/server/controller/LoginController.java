package com.danram.server.controller;

import com.danram.server.domain.member.Member;
import com.danram.server.dto.response.LoginResponseDto;
import com.danram.server.oauth.SocialLoginType;
import com.danram.server.service.login.GoogleLoginService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiOperation("회원가입과 로그인을 진행한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
    })
    public ResponseEntity<LoginResponseDto> requestToken(
            @PathVariable(name = "socialLoginType") SocialLoginType socialLoginType,
            @RequestParam(name = "code") String code) {
        final Member member = googleLoginService.signUp();

        return ResponseEntity.ok(googleLoginService.generateTokens(member));

    }
}
