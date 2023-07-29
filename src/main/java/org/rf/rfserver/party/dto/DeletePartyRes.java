package org.rf.rfserver.party.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DeletePartyRes {
    Long id;

    @Builder
    public DeletePartyRes(Long id) {
        this.id = id;
    }
}