package com.danram.server.controller;

import com.danram.server.domain.Member;
import com.danram.server.service.member.MemberService;
import com.danram.server.service.oauth.GoogleLoginService;
import com.danram.server.util.JwtUtil;
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
    public ResponseEntity<Member> getMyUserInfo() {
        final String accessToken = JwtUtil.getAccessToken();

        return ResponseEntity.ok(memberService.getInfo(accessToken));
    }

    @GetMapping("/info/{username}")
    public ResponseEntity<Member> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(googleLoginService.getUserWithAuthorities(username).get());
    }

    @GetMapping("/change/{id}")
    public Long changeId(@RequestParam(name = "id") Long id) {
        return googleLoginService.setId(id);
    }
}
