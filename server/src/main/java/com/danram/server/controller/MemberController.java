package com.danram.server.controller;

import com.danram.server.domain.member.Member;
import com.danram.server.service.firebase.FirestoreService;
import com.danram.server.service.member.MemberService;
import com.danram.server.service.login.GoogleLoginService;
import com.danram.server.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {
    private final GoogleLoginService googleLoginService;
    private final MemberService memberService;
    private final FirestoreService firestoreService;

    public MemberController(final GoogleLoginService googleLoginService, final MemberService memberService, final FirestoreService firestoreService) {
        this.googleLoginService = googleLoginService;
        this.memberService = memberService;
        this.firestoreService = firestoreService;
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

    @GetMapping("/info/{userId}")
    @ApiOperation("특정 사용자의 정보를 받아온다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "해당 정보를 가진 Member가 없음"),
            @ApiResponse(responseCode = "403", description = "해당 사용자가 관리자 권한이 아님"),
            @ApiResponse(responseCode = "401", description = "해당 사용자가 인증되지 않음 | 토큰 만료")
    })
    public ResponseEntity<Member> getUserInfo(@PathVariable Long userId) {
        return ResponseEntity.ok(memberService.findMemberById(userId));
    }

    @GetMapping("/change/{id}")
    @ApiOperation("id를 변경한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "해당 정보를 가진 ADMIN가 없음"),
            @ApiResponse(responseCode = "403", description = "해당 사용자가 ADMIN 권한이 아님"),
            @ApiResponse(responseCode = "401", description = "해당 사용자가 인증되지 않음 | 토큰 만료")
    })
    public Long changeId(@PathVariable Long id) {
        return googleLoginService.setId(id);
    }

    @GetMapping("/name/{name}")
    @ApiOperation("닉네임 변경")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "해당 정보를 가진 Member가 없음"),
            @ApiResponse(responseCode = "403", description = "해당 사용자가 Member 권한이 아님"),
            @ApiResponse(responseCode = "401", description = "해당 사용자가 인증되지 않음 | 토큰 만료")
    })
    public ResponseEntity<Member> changeName(@PathVariable String name) {
        log.info("called >> change name");
        return ResponseEntity.ok(memberService.changeName(name));
    }

    @PostMapping("/profile/img")
    @ApiOperation("프로필 사진을 변경한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "해당 정보를 가진 Member가 없음"),
            @ApiResponse(responseCode = "403", description = "해당 사용자가 Member 권한이 아님"),
            @ApiResponse(responseCode = "401", description = "해당 사용자가 인증되지 않음 | 토큰 만료")
    })
    public ResponseEntity<Member> changeProfileImg(@RequestParam(value = "file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("File is not exist");
        }

        final String img = firestoreService.uploadFiles(file, file.getOriginalFilename());

        return ResponseEntity.ok(memberService.changeProfileImg(img));
    }

    @GetMapping("/delete")
    @ApiOperation("회원 탈퇴")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "해당 정보를 가진 Member가 없음"),
            @ApiResponse(responseCode = "403", description = "해당 사용자가 Member 권한이 아님"),
            @ApiResponse(responseCode = "401", description = "해당 사용자가 인증되지 않음 | 토큰 만료")
    })
    public ResponseEntity deleteAccount() {
        memberService.deleteAccount();

        return ResponseEntity.ok().build();
    }
}
