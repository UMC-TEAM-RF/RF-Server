package org.rf.rfserver.party.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinPartyReq {
    private Long userId;
    private Long partyId;
}
