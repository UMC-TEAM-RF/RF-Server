package org.rf.rfserver.party.dto;

import lombok.Getter;

@Getter
public class PostPartyJoinReq {
    private Long userId;
    private Long partyId;
}
