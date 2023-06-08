package com.danram.server.service.party;

import com.danram.server.domain.party.Party;
import com.danram.server.domain.party.PartyInfo;
import com.danram.server.domain.party.PartyMembers;
import com.danram.server.domain.post.Feed;
import com.danram.server.dto.request.*;
import com.danram.server.exception.party.DuplicatePartyException;
import com.danram.server.exception.party.PartyNotFoundException;
import com.danram.server.repository.FeedRepository;
import com.danram.server.repository.PartyMembersRepository;
import com.danram.server.repository.PartyRepository;
import com.danram.server.service.member.MemberService;
import com.danram.server.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartyServiceImpl implements PartyService {
    private final PartyRepository  partyRepository;
    private final MemberService memberService;
    private final PartyMembersRepository partyMembersRepository;
    private final FeedRepository feedRepository;
    private final ImgSrc imgSrc = new ImgSrc();

    @Override
    public PartyMembers createParty(final PartyInfoDto partyInfoDto) {
        Long id = memberService.getId(JwtUtil.getAccessToken());

        //중복 체크
        if(duplicateCheckName(partyInfoDto.getGroupName())) {
            throw new DuplicatePartyException(partyInfoDto.getGroupName());
        }

        partyRepository.save(Party.of(partyInfoDto, id));

        final Party party = partyRepository.findPartyByGroupName(partyInfoDto.getGroupName()).orElseThrow();

        feedRepository.save(Feed.of(party.getPartyId()));

        PartyInfo partyInfo = new PartyInfo();

        partyInfo.setPartyId(party.getPartyId());
        partyInfo.setUserId(id);

        //return partyMembersRepository.findById(partyInfo).orElseThrow();
        return partyMembersRepository.save(new PartyMembers(party.getPartyId(), id));
    }

    @Override
    public PartyMembers createParty(final PartyInfoImgNotDto partyInfoImgNotDto) {
        Long id = memberService.getId(JwtUtil.getAccessToken());

        //중복 체크
        if(duplicateCheckName(partyInfoImgNotDto.getGroupName())) {
            throw new DuplicatePartyException(partyInfoImgNotDto.getGroupName());
        }

        if(partyInfoImgNotDto.getGroupType().equals("스터디")) {
            partyRepository.save(Party.convert(partyInfoImgNotDto, id, imgSrc.getCcding()));
        }
        else if(partyInfoImgNotDto.getGroupType().equals("독서")) {
            partyRepository.save(Party.convert(partyInfoImgNotDto, id, imgSrc.getReading()));
        }
        else if(partyInfoImgNotDto.getGroupType().equals("취미")) {
            partyRepository.save(Party.convert(partyInfoImgNotDto, id, imgSrc.getHobby()));
        }
        else if(partyInfoImgNotDto.getGroupType().equals("운동/스포츠")) {
            partyRepository.save(Party.convert(partyInfoImgNotDto, id, imgSrc.getExercise()));
        }
        else if(partyInfoImgNotDto.getGroupType().equals("문화/예술")) {
            partyRepository.save(Party.convert(partyInfoImgNotDto, id, imgSrc.getArt()));
        }
        else if(partyInfoImgNotDto.getGroupType().equals("생활습관")) {
            partyRepository.save(Party.convert(partyInfoImgNotDto, id, imgSrc.getLife()));
        }
        else if(partyInfoImgNotDto.getGroupType().equals("여행")) {
            partyRepository.save(Party.convert(partyInfoImgNotDto, id, imgSrc.getTravel()));
        }
        else if(partyInfoImgNotDto.getGroupType().equals("반려동물")) {
            partyRepository.save(Party.convert(partyInfoImgNotDto, id, imgSrc.getAnimal()));
        }
        else if(partyInfoImgNotDto.getGroupType().equals("다이어트")) {
            partyRepository.save(Party.convert(partyInfoImgNotDto, id, imgSrc.getDiet()));
        }
        else
        {
            partyRepository.save(Party.convert(partyInfoImgNotDto, id, imgSrc.getFree()));
        }

        final Party party = partyRepository.findPartyByGroupName(partyInfoImgNotDto.getGroupName()).orElseThrow();

        feedRepository.save(Feed.of(party.getPartyId()));

        //return partyMembersRepository.save(new PartyMembers(party.getPartyId(), id));
        return partyMembersRepository.save(new PartyMembers(party.getPartyId(), id));
    }

    @Override
    public void deleteParty(final PartyIdDto partyIdDto) {
        Party target = partyRepository.findById(partyIdDto.getId()).orElseThrow(() -> new PartyNotFoundException(partyIdDto.getId()));

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

        if(result.isEmpty()){
            return parties;
        }

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
        return partyRepository.findById(partyIdDto.getId()).orElseThrow(() -> new PartyNotFoundException(partyIdDto.getId()));
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
    public void removeMember(Long partyId) {
        Long userId = memberService.getId(JwtUtil.getAccessToken());

        partyMembersRepository.deleteByPartyIdAndUserId(partyId, userId);
    }

    @Override
    public Party changeAlarmTime(AlarmDto alarmDto) {
        final Party party = partyRepository.findById(alarmDto.getId()).orElseThrow();

        party.setAlarmTime(alarmDto.getTime());

        return partyRepository.save(party);
    }

    @Override
    public boolean duplicateCheckName(final String name) {
        final Optional<Party> result = partyRepository.findPartyByGroupName(name);

        if(result.isEmpty()) {
            return false;
        }
        else
        {
            return true;
        }
    }
}
