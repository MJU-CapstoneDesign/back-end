package com.danram.server.controller;

import com.danram.server.dto.response.GoogleAuthResponseDto;
import com.danram.server.oauth.SocialLoginType;
import com.danram.server.service.oauth.GoogleOauthService;
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
    private final GoogleOauthService googleOauthService;

    /**
     * 사용자로부터 SNS 로그인 요청을 Social Login Type 을 받아 처리
     * @param socialLoginType (GOOGLE, FACEBOOK, NAVER, KAKAO)
     */
    @GetMapping(value = "/{socialLoginType}")
    public void socialLoginType(
            @PathVariable(name = "socialLoginType") SocialLoginType socialLoginType) {
        log.info(">> 사용자로부터 SNS 로그인 요청을 받음 :: {} Social Login", socialLoginType);
        googleOauthService.request(socialLoginType);
    }

    /**
     * Social Login API Server 요청에 의한 callback 을 처리
     * @param socialLoginType (GOOGLE, FACEBOOK, NAVER, KAKAO)
     * @param code API Server 로부터 넘어노는 code
     * @return SNS Login 요청 결과로 받은 Json 형태의 String 문자열 (access_token, refresh_token 등)
     */
    @PostMapping(value = "/{socialLoginType}/token")
    public ResponseEntity<GoogleAuthResponseDto> requestJwtToken(
            @PathVariable(name = "socialLoginType") SocialLoginType socialLoginType,
            @RequestBody String code) {
        log.info(">> 소셜 로그인 API 서버로부터 받은 code :: {}", code);

        final GoogleAuthResponseDto response = googleOauthService.requestAccessToken(socialLoginType, code);

        log.info(">> 소셜 로그인 API 서버로부터 받은 결과:: {}", response);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{socialLoginType}/token")
    public ResponseEntity<GoogleAuthResponseDto> generateToken(
            @PathVariable(name = "socialLoginType") SocialLoginType socialLoginType,
            @RequestParam(name = "code") String code) {
        log.info(">> 소셜 로그인 API 서버로부터 받은 code :: {}", code);

        final GoogleAuthResponseDto response = googleOauthService.requestAccessToken(socialLoginType, code);

        log.info(">> 소셜 로그인 API 서버로부터 받은 결과:: {}", response);

        return ResponseEntity.ok(response);
    }
}
