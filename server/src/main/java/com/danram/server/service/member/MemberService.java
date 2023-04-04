package com.danram.server.service.member;

import com.danram.server.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface MemberService{
    public Optional<Member> findMemberByUserId(Long id);
    public Member getInfo(String accessToken);
}
