package com.danram.server.domain.party;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PartyInfo implements Serializable {
    private Long userId;
    private Long partyId;
}
