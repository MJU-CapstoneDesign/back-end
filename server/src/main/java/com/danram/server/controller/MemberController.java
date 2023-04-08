package com.danram.server.controller;

import com.danram.server.domain.Member;
import com.danram.server.service.member.MemberService;
import com.danram.server.service.oauth.GoogleLoginService;
import com.danram.server.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {
    private final GoogleLoginService googleLoginService;
    private final MemberService memberService;

    public MemberController(final GoogleLoginService googleLoginService, final MemberService memberService) {
        this.googleLoginService = googleLoginService;
        this.memberService = memberService;
    }

    @GetMapping("/info")
    @ApiOperation("자신의 정보를 받아온다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "해당 정보를 가진 Member가 없음"),
            @ApiResponse(responseCode = "403", description = "해당 사용자가 Member 권한이 아님"),
            @ApiResponse(responseCode = "401", description = "해당 사용자가 인증되지 않음 | 토큰 만료")
    })
    public ResponseEntity<Member> getMyUserInfo() {
        final String accessToken = JwtUtil.getAccessToken();

        return ResponseEntity.ok(memberService.getInfo(accessToken));
    }

    @GetMapping("/info/{username}")
    @ApiOperation("특정 사용자의 정보를 받아온다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "해당 정보를 가진 Member가 없음"),
            @ApiResponse(responseCode = "403", description = "해당 사용자가 Member 권한이 아님"),
            @ApiResponse(responseCode = "401", description = "해당 사용자가 인증되지 않음 | 토큰 만료")
    })
    public ResponseEntity<Member> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(googleLoginService.getUserWithAuthorities(username).get());
    }

    @GetMapping("/change/{id}")
    @ApiOperation("id를 변경한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "해당 정보를 가진 ADMIN가 없음"),
            @ApiResponse(responseCode = "403", description = "해당 사용자가 ADMIN 권한이 아님"),
            @ApiResponse(responseCode = "401", description = "해당 사용자가 인증되지 않음 | 토큰 만료")
    })
    public Long changeId(@RequestParam(name = "id") Long id) {
        return googleLoginService.setId(id);
    }
}
