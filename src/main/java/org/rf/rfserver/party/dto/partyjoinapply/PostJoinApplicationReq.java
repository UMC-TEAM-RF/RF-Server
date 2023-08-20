package org.rf.rfserver.party.dto.partyjoinapply;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostJoinApplicationReq {
    private Long userId;
    private Long partyId;
}
