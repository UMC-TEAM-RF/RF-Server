package org.rf.rfserver.party.dto;

import lombok.Getter;

@Getter
public class JoinPartyRes {
    private Long id;

    public JoinPartyRes(Long id) {
        this.id = id;
    }
}
