package com.danram.server.controller;

import com.danram.server.domain.member.Authority;
import com.danram.server.service.member.MemberService;
import com.danram.server.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final MemberService memberService;

    @GetMapping("/info")
    @ApiOperation("사용자 권한 확인 api")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "해당 정보를 가진 Member가 없음"),
            @ApiResponse(responseCode = "403", description = "해당 사용자가 Member 권한이 아님"),
            @ApiResponse(responseCode = "401", description = "해당 사용자가 인증되지 않음 | 토큰 만료")
    })
    public ResponseEntity<List<Authority>> getAuthorities() {
        return ResponseEntity.ok(memberService.getInfo(JwtUtil.getAccessToken()).getAuthorities());
    }
}
