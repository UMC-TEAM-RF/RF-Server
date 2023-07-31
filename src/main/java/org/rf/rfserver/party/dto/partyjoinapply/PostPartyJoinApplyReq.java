package org.rf.rfserver.party.dto.partyjoinapply;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostPartyJoinApplyReq {
    private Long userId;
    private Long partyId;
}
