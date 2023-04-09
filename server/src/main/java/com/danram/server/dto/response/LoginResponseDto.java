package com.danram.server.dto.response;

import com.danram.server.domain.member.Member;
import com.danram.server.util.JwtUtil;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginResponseDto {
    private String accessToken;
    private String refreshToken;

    public static LoginResponseDto of(Member member) {
        return LoginResponseDto.builder()
                .accessToken(JwtUtil.createJwt(member))
                .refreshToken(JwtUtil.createRefreshToken())
                .build();
    }
}