package org.rf.rfserver.party.dto.partyjoin;

import lombok.Getter;

@Getter
public class PostApprovePartyJoinRes {
    Long id;

    public PostApprovePartyJoinRes(Long id) {
        this.id = id;
    }
}
