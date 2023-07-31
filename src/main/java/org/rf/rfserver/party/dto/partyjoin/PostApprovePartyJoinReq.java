package org.rf.rfserver.party.dto.partyjoin;

import lombok.Getter;

@Getter
public class PostApprovePartyJoinReq {
    private Long userId;
    private Long partyId;
    private Long partyJoinApplyId;
}
