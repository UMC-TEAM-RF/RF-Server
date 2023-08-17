package org.rf.rfserver.party.dto.party;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DeletePartyRes {
    Long id;

    public DeletePartyRes(Long id) {
        this.id = id;
    }
}