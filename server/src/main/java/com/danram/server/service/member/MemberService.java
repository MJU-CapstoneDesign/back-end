package com.danram.server.service.member;

import com.danram.server.domain.member.Member;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface MemberService{
    public Optional<Member> findMemberByUserId(Long id);
    public Member findMemberById(Long id);
    public Member getInfo(String accessToken);
    public Long getId(String accessToken);
}
