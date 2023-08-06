package org.rf.rfserver.party.dto.partyjoin;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostApproveJoinReq {
    private Long userId;
    private Long partyId;
    private Long partyJoinApplicationId;
}
