package org.rf.rfserver.party.dto.partyJoin;

import lombok.Getter;

@Getter
public class PostPartyJoinReq {
    private Long userId;
    private Long partyId;
}
