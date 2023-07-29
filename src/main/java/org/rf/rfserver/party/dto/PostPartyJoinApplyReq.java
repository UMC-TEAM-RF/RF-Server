package org.rf.rfserver.party.dto;

import lombok.Getter;

@Getter
public class PostPartyJoinApplyReq {
    private Long userId;
    private Long partyId;
}
