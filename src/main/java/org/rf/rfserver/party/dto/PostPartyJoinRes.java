package org.rf.rfserver.party.dto;

import lombok.Getter;

@Getter
public class PostPartyJoinRes {
    private Long id;

    public PostPartyJoinRes(Long id) {
        this.id = id;
    }
}
