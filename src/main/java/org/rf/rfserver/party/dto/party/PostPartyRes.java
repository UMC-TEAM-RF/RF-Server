package org.rf.rfserver.party.dto.party;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostPartyRes {
    private Long id;

    @Builder
    public PostPartyRes(Long id) {
        this.id = id;
    }
}
