package com.danram.server.service.oauth;

import com.danram.server.domain.member.Authority;
import com.danram.server.domain.member.Member;
import com.danram.server.domain.member.Tokens;
import com.danram.server.repository.MemberRepository;
import com.danram.server.dto.response.LoginResponseDto;
import com.danram.server.repository.MemberNameRepository;
import com.danram.server.repository.TokensRepository;
import com.danram.server.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleLoginServiceImpl implements GoogleLoginService {
    private final MemberRepository memberRepository;
    private final TokensRepository tokensRepository;
    private final MemberNameRepository memberNameRepository;
    private static Long ID = 1L;

    @Override
    public LoginResponseDto generateTokens(Member member) {
        final LoginResponseDto response = LoginResponseDto.of(member);

        Tokens tokens = Tokens.builder()
                .userId(member.getUserId())
                .accessToken(response.getAccessToken())
                .refreshToken(response.getRefreshToken())
                .build();

        tokensRepository.save(tokens);

        tokensRepository.findById(member.getUserId()).orElseThrow();

        return response;
    }

    @Override
    @Transactional
    public Member signUp() {
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        Member member = Member.builder()
                .name(memberNameRepository.findById(ID).get().getName())
                .profile(memberNameRepository.findById(ID++).get().getImg())
                .authorities(Arrays.asList(authority))
                .build();
        memberRepository.save(member);

        return memberRepository.findMemberByName(member.getName()).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Member> getUserWithAuthorities() {
        return SecurityUtil.getCurrentUsername().flatMap(memberRepository::findOneWithAuthoritiesByName);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Member> getUserWithAuthorities(String name) {
        return memberRepository.findOneWithAuthoritiesByName(name);
    }

    @Override
    public Long setId(final Long id) {
        ID = id;

        log.info(">> ID: {}", ID);

        return ID;
    }
}