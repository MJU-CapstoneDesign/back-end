package com.danram.server.service.party;

import com.danram.server.domain.party.Party;
import com.danram.server.domain.party.PartyMembers;
import com.danram.server.dto.request.AlarmDto;
import com.danram.server.dto.request.MemberIdDto;
import com.danram.server.dto.request.PartyIdDto;
import com.danram.server.dto.request.PartyInfoDto;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface PartyService {
    public PartyMembers createParty(PartyInfoDto partyInfoDto);
    public void deleteParty(PartyIdDto partyIdDto);
    public List<Party> findAll();
    public List<Party> findParty();
    public List<Party> findPartyById(MemberIdDto memberIdDto); // 관리자 권한
    public Party findPartyById(PartyIdDto partyIdDto);
    public PartyMembers addMember(PartyIdDto partyIdDto);
    public void removeMember(Long partyId);
    public Party changeAlarmTime(AlarmDto alarmDto);
    public boolean duplicateCheckName(String name);
}
