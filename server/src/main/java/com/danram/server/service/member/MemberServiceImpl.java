package com.danram.server.service.member;

import com.danram.server.domain.member.Member;
import com.danram.server.exception.member.MemberNotFoundException;
import com.danram.server.repository.MemberRepository;
import com.danram.server.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {
    private MemberRepository memberRepository;

    public MemberServiceImpl(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Optional<Member> findMemberByUserId(final Long id) {
        return memberRepository.findMemberByUserId(id);
    }

    @Override
    public Member findMemberById(Long id) {
        return memberRepository.findMemberByUserId(id).orElseThrow(() -> new MemberNotFoundException(id));
    }

    @Override
    public Member getInfo(String accessToken) {
        final Long id = JwtUtil.getMemberId(accessToken).getId();

        return memberRepository.findMemberByUserId(id).orElseThrow(() -> new MemberNotFoundException(id));
    }

    @Override
    public Long getId(String accessToken) {
        return getInfo(accessToken).getUserId();
    }
}
