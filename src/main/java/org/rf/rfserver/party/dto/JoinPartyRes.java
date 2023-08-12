package org.rf.rfserver.party.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinPartyRes {
    private Long userId;
    private Long partyId;

    @Builder
    public JoinPartyRes(Long userId, Long partyId) {
        this.userId = userId;
        this.partyId = partyId;
    }
}
