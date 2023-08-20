package org.rf.rfserver.party.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class JoinPartyReq {
    private Long userId;
    private Long partyId;
}
