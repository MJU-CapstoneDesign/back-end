package com.danram.server.service.member;

import com.danram.server.domain.member.Member;
import com.danram.server.domain.party.Party;
import com.danram.server.domain.party.PartyMembers;
import com.danram.server.exception.member.MemberNotFoundException;
import com.danram.server.exception.party.PartyNotFoundException;
import com.danram.server.repository.MemberRepository;
import com.danram.server.repository.PartyMembersRepository;
import com.danram.server.repository.TokensRepository;
import com.danram.server.service.party.PartyService;
import com.danram.server.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {
    private final PartyMembersRepository partyMembersRepository;
    private final TokensRepository tokensRepository;
    private MemberRepository memberRepository;
    private PartyService partyService;

    public MemberServiceImpl(final MemberRepository memberRepository,
                             final TokensRepository tokensRepository,
                             final PartyMembersRepository partyMembersRepository) {
        this.memberRepository = memberRepository;
        this.tokensRepository = tokensRepository;
        this.partyMembersRepository = partyMembersRepository;
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
        final Long id = JwtUtil.getMemberId().getId();

        return memberRepository.findMemberByUserId(id).orElseThrow(() -> new MemberNotFoundException(id));
    }

    @Override
    public Long getId(String accessToken) {
        return getInfo(accessToken).getUserId();
    }

    @Override
    public Member changeName(final String name) {
        log.info(">> called change metho");
        Member member = memberRepository.findMemberByUserId(JwtUtil.getMemberId().getId()).orElseThrow(() -> new MemberNotFoundException(JwtUtil.getMemberId().getId()));

        log.info(">> called");

        member.setName(name);

        return memberRepository.save(member);
    }

    @Override
    public Member changeProfileImg(final String img) {
        Member member = memberRepository.findMemberByUserId(JwtUtil.getMemberId().getId()).orElseThrow(() -> new MemberNotFoundException(JwtUtil.getMemberId().getId()));

        member.setProfile(img);

        return memberRepository.save(member);
    }

    @Override
    public void deleteAccount() {
        final Optional<Member> member = memberRepository.findMemberByUserId(JwtUtil.getMemberId().getId());

        Long id = member.get().getUserId();

        memberRepository.delete(member.orElseThrow(() -> new MemberNotFoundException(JwtUtil.getMemberId().getId())));
        tokensRepository.deleteById(id);

        if(!partyMembersRepository.findPartyMembersByUserId(id).isEmpty()) {
            partyMembersRepository.deleteByUserId(id);
        }
    }
}
