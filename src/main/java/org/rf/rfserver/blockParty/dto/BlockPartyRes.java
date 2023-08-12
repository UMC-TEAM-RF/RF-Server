package org.rf.rfserver.blockParty.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlockPartyRes {
    private Long userId;
    private Long partyId;

    @Builder
    public BlockPartyRes(Long userId, Long partyId) {
        this.userId = userId;
        this.partyId = partyId;
    }
}
