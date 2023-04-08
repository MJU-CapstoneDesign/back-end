package com.danram.server.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartyMembersDto {
    private Long partyId;
    private Long userId;
}
