package com.danram.server.repository;

import com.danram.server.domain.party.Party;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartyRepository extends JpaRepository<Party, Long> {
    public Optional<Party> findPartyByOwnerId(Long id);
}
