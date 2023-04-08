package com.danram.server.service.party;

import com.danram.server.domain.member.Member;
import com.danram.server.domain.party.Party;
import com.danram.server.domain.party.PartyMembers;
import com.danram.server.dto.request.AlarmDto;
import com.danram.server.dto.request.MemberIdDto;
import com.danram.server.dto.request.PartyIdDto;
import com.danram.server.dto.request.PartyInfoDto;
import com.danram.server.exception.party.PartyNotFoundException;
import com.danram.server.repository.PartyMembersRepository;
import com.danram.server.repository.PartyRepository;
import com.danram.server.service.member.MemberService;
import com.danram.server.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartyServiceImpl implements PartyService {
    private final PartyRepository  partyRepository;
    private final MemberService memberService;
    private final PartyMembersRepository partyMembersRepository;

    @Override
    public PartyMembers createParty(final PartyInfoDto partyInfoDto) {
        Long id = memberService.getId(JwtUtil.getAccessToken());

        partyRepository.save(Party.of(partyInfoDto, id));

        final Party party = partyRepository.findPartyByOwnerId(id).orElseThrow();

        return partyMembersRepository.save(new PartyMembers(party.getPartyId(), id));
    }

    @Override
    public void deleteParty(final PartyIdDto partyIdDto) {
        Party target = partyRepository.findById(partyIdDto.getId()).orElseThrow();

        partyRepository.delete(target);

        partyMembersRepository.deleteByPartyId(partyIdDto.getId());
    }

    @Override
    public List<Party> findAll() {
        return partyRepository.findAll();
    }

    @Override
    public List<Party> findParty() {
        Long userId = memberService.getId(JwtUtil.getAccessToken());
        List<PartyMembers> result = partyMembersRepository.findPartyMembersByUserId(userId); // 자신이 속한 파티 정보들
        List<Party> parties = new ArrayList<>();

        for(PartyMembers i : result) {
            parties.add(partyRepository.findById(i.getPartyId()).orElseThrow());
        }

        return parties;
    }

    @Override
    public List<Party> findPartyById(final MemberIdDto memberIdDto) {
        log.info(">> user id: {}", memberIdDto.getId());

        List<PartyMembers> result = partyMembersRepository.findPartyMembersByUserId(memberIdDto.getId());

        List<Party> parties = new ArrayList<>();

        for(PartyMembers i : result) {
            parties.add(partyRepository.findById(i.getPartyId()).orElseThrow());
        }

        return parties;
    }

    @Override
    public Party findPartyById(final PartyIdDto partyIdDto) {
        return partyRepository.findById(partyIdDto.getId()).orElseThrow();
    }

    @Override
    public PartyMembers addMember(final PartyIdDto partyIdDto) {
        log.info(">> partyIdDto: {}", partyIdDto.getId().toString());
        partyRepository.findById(partyIdDto.getId()).orElseThrow(() -> new PartyNotFoundException(partyIdDto.getId()));

        PartyMembers result = PartyMembers.builder()
                .partyId(partyIdDto.getId())
                .userId(memberService.getId(JwtUtil.getAccessToken()))
                .build();

        return partyMembersRepository.save(result);
    }

    @Transactional
    @Override
    public void removeMember() {
        Long userId = memberService.getId(JwtUtil.getAccessToken());

        partyMembersRepository.deleteByUserId(userId);
    }

    @Override
    public Party changeAlarmTime(AlarmDto alarmDto) {
        final Party party = partyRepository.findById(alarmDto.getId()).orElseThrow();

        party.setAlarmTime(alarmDto.getTime());

        return partyRepository.save(party);
    }
}
