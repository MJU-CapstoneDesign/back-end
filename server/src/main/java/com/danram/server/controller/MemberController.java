package com.danram.server.controller;

import com.danram.server.domain.Member;
import com.danram.server.service.member.MemberService;
import com.danram.server.service.oauth.GoogleLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

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
    //@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Member> getMyUserInfo(HttpServletRequest httpServletRequest) {
        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        String token = header.split(" ")[1];

        memberService.getInfo(token);

        return ResponseEntity.ok(memberService.getInfo(token));
    }

    @GetMapping("/info/{username}")
    public ResponseEntity<Member> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(googleLoginService.getUserWithAuthorities(username).get());
    }
}
