package com.danram.server.repository;

import com.danram.server.domain.party.PartyInfo;
import com.danram.server.domain.party.PartyMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartyMembersRepository extends JpaRepository<PartyMembers, PartyInfo> {
    public List<PartyMembers> findPartyMembersByUserId(Long id);
    public void deleteByUserId(Long id);
    public void deleteByPartyId(Long id);
}
